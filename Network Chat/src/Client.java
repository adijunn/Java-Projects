import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client extends JFrame {

	private JPanel contentPane;

	private String name, ipaddress;
	private int port;
	private JTextField txtTextField;
	JTextArea txtrTextArea;
	
	DatagramSocket socket;
	InetAddress ip;
	
	private Thread send, receive;

	/**
	 * Create the frame.
	 */
	public Client(String name, String ipaddress, int port) {
		setTitle("Client Window");
		this.name = name;
		this.ipaddress = ipaddress;
		this.port = port;
		boolean connected = openConnection(ipaddress, port);
		if(!connected) {
			System.err.println("Connection Failed!");
		}
		createWindow();
		console("Successfully Connected!");
		String connection = name;
		connection = "/c/" + connection;
		sendMessage(connection.getBytes());
		receive();
		
	}

	public void send(String message) {
		//console(name + "> " + message);
		message = "/m/" + name + "> " + message;
		sendMessage(message.getBytes());
		txtTextField.setText("");
		txtTextField.requestFocusInWindow();
	}
	
	private boolean openConnection(String address, int port) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return false;
		}
		catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void receive() {
		System.out.println("Got the message from server");
		receive = new Thread("Receive") {
		public void run() {
			while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String message = new String(packet.getData());
			console(message);
		}
		}
		};
		receive.start();
	}
	private void sendMessage(final byte[] data) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket payload = new DatagramPacket(data, data.length, ip, port); 
				try {
					socket.send(payload);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		};
		send.start();
	}

	private void console(String message) {
		txtrTextArea.append(message + "\n\r");
	}

	private void createWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtrTextArea = new JTextArea();
		txtrTextArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(txtrTextArea);
		txtrTextArea.setBounds(0, 6, 497, 300);
		contentPane.add(txtrTextArea);
		contentPane.add(scroll);
		contentPane.revalidate();
		
		txtTextField = new JTextField();
		txtTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(txtTextField.getText());
				}
			}
		});
		txtTextField.setText("");
		txtTextField.setBounds(0, 307, 410, 28);
		contentPane.add(txtTextField);
		txtTextField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Pressed");
				send(txtTextField.getText());
			}

		});
		btnSend.setBounds(404, 308, 93, 29);
		contentPane.add(btnSend);
		setVisible(true);
		
		txtTextField.requestFocusInWindow();
	}
	
	

	
}
