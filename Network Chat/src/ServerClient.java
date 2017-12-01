import java.net.InetAddress;


public class ServerClient {
	String name;
	InetAddress ip;
	int port;
	
	
	public ServerClient(String name, InetAddress ip, int port) {
		this.name = name;
		this.ip = ip;
		this.port = port;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}
	
	public InetAddress getIP() {
		return ip;
	}
	
}
