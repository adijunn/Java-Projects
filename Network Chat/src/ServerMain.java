import java.awt.Dimension;

import javax.swing.JFrame;


public class ServerMain extends JFrame{

	
	public ServerMain(int port) {
		new Server(port);
		init();
	}

	public static void main(String[] args) {
		int port;
		if(args.length != 1) {
			System.out.println("Only one parameter [port]");
			return;
		}
		
			port = Integer.parseInt(args[0]);
			new ServerMain(port);
		
	}
	
	public void init(){
		this.setSize(new Dimension(50, 100));
		this.setResizable(false);
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
