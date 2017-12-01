package Server;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ServerMain extends JFrame{
	
	private int port;
	private Server server;
	
	public ServerMain(int port){
		this.port = port;
		server = new Server(port);
		this.setSize(new Dimension(100,100));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		new ServerMain(8192);
	}
}
