package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class Server implements Runnable{
	
	private List<ServerClient> clients = new ArrayList<ServerClient>();
	private int port;
	private boolean running = false;
	private DatagramSocket socket;
	
	private Thread run, manage, send, receive;
	
	public Server(int p){
		port = p;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		run = new Thread(this, "Server");
		run.start();
	}

	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
		manageClients();
		receive();
	}
	
	private void manageClients(){
		manage = new Thread("Manage"){
			public void run(){
				while(running){
					//Managing
				}
			}
		};
		manage.start();
	}
	
	private void receive(){
		receive = new Thread("Receive"){
			public void run(){
				while(running){
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					process(packet);
				}
			}
		};
		receive.start();
	}
	
	//       /c/name
	private void process(DatagramPacket packet){
		String string = new String(packet.getData());
		if(string.startsWith("/c/")){
			int id = UniqueIdentifier.getIdentifier();
			ServerClient client = new ServerClient(string.substring(3), packet.getAddress(), packet.getPort(), id);
			clients.add(client);
			send(("/c/" + id).getBytes(), client.address, client.port);
			System.out.println("New Client: " + id);
		}else if(string.startsWith("/m/")){
			String message = string;
			sendToAll(message);
		}else if(string.startsWith("/locked/")){
			sendToAll(string);
		}else if(string.startsWith("/restore/")){
			sendToAll(string);
		}else{
			System.out.println(string);
		}
	}
	
	private void sendToAll(String message){
		for(int i = 0; i < clients.size(); i++){
			ServerClient client = clients.get(i);
			send(message.getBytes(), clients.get(i).address, clients.get(i).port);
		}
	}
	
	private void send(final byte[] data, final InetAddress address, final int port){
		String message = new String(data);
		System.out.println("Sending to: " + address + ":" + port);
		System.out.println(message.trim());
		send = new Thread(){
			public void run(){
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
}
