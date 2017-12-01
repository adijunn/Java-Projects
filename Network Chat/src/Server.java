import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


public class Server implements Runnable{
	
	private ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	
	private int port;
	private DatagramSocket socket;
	private boolean running = false;
	
	private Thread run, manageClients, receive, send;
	
	public Server(int port) {
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		run = new Thread(this, "Server");
		run.start();
	}
	
	

	@Override
	public void run() {
		running = true;
		System.out.println("Server started on " + port);
		manageClients();
		receive();
	}



	private void receive() {
		receive = new Thread("Receive") {
			public void run() {
				while(running) {
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet); //puts incoming data into payload
					} catch (IOException e) {
						e.printStackTrace();
					}
					String message = new String(packet.getData());
					if(message.substring(0, 3).equals("/m/")) {
						System.out.println("this is admin");
						if(packet.getAddress() == socket.getInetAddress()) {
							if(message.contains("kick")) {
								for(ServerClient client : clients) {
								if(message.contains(client.getName())) {
									for(ServerClient person : clients) {
										if(person.getName().equals(client.getName())) {
											clients.remove(person);
										}
									}
								}
								}
							}
							else {
								sendToAll(message.substring(3));
							}
						}
						sendToAll(message.substring(3));
					}
					else if(message.substring(0, 3).equals("/c/")) {
						clients.add(new ServerClient(message.substring(3), packet.getAddress(), packet.getPort()));
					}
					
				}
			}
		};
		receive.start();
	}

			public void sendToAll(final String message) {
				send = new Thread("Send") {
				public void run() {
					for(ServerClient client : clients) {
						DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, client.getIP(), client.getPort());
						try {
							socket.send(packet);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
				send.start();
			}



	private void manageClients() {
		manageClients = new Thread("Manage") {
			public void run() {
				while(running) {
					
				}
			}
		};
		manageClients.start();
	}

}
