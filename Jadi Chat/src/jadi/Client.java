package jadi;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client{

	//For Gui Purposes
	private Thread sendThread, receiveThread;
	
	//For Client Purposes
	private String address, name;
	boolean connection = false, running = false;
	
	//For Connection purposes
	private DatagramSocket socket;
	private InetAddress ip;
	private int port, ID = -1;
	
	public Client(String name, String address, int port){
		this.name = name;
		this.address = address;
		this.port = port;
	}

	public String receive(){
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;
	}
	
	public void send(final byte[] data){
		sendThread = new Thread("Send"){
			public void run(){
				System.out.println("Sending stuff -> " + new String(data));
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		sendThread.start();
	}
	
	public boolean openConnection(String address, int port){
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		running = true;
		String connection = "/c/" + name;
		send(connection.getBytes());
		return true;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public int getPort(){
		return port;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	
	public int getID(){
		return ID;
	}
	
	public void connect(){
		connection = openConnection(address, port);
		System.out.println("Trying to connect!");
	}

}
