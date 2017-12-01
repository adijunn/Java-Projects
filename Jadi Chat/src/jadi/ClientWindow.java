package jadi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientWindow extends JFrame implements KeyListener, FocusListener{

	private JTextField userInput, ipInput, username;
	private JTextArea messageOutput;
	private GridBagLayout gbLayout;
	
	private final int WIDTH = 500, HEIGHT = 600;
	private boolean hasConnected = false, colorInverted = false;
	private int port;
	private String address, name;
	boolean connection = false;
	private Client client;
	private Thread listen;
	
	public ClientWindow(){
		name = "Unknown";
		address = "none";
		port = -1;
		
		//INPUT FIELDS
		userInput = new JTextField();
		userInput.setPreferredSize(new Dimension(WIDTH, 27));
		userInput.addKeyListener(this);
		userInput.setText("You must enter a username and initiate a connect before typing.");
		userInput.setEditable(false);
		
		username = new JTextField();
		username.setPreferredSize(new Dimension(WIDTH / 2, 27));
		username.addKeyListener(this);
		username.addFocusListener(this);
		username.setHorizontalAlignment(JTextField.CENTER);
		username.setForeground(Color.BLUE);
		username.setText("-- Enter Username --");
		username.setToolTipText("The name displayed to other users.");

		ipInput = new JTextField();
		ipInput.setPreferredSize(new Dimension(WIDTH / 2, 27));
		ipInput.setHorizontalAlignment(JTextField.CENTER);
		ipInput.addKeyListener(this);
		ipInput.addFocusListener(this);
		ipInput.setForeground(Color.BLUE);
		ipInput.setText("-- Enter IP --");
		ipInput.setToolTipText("[Port]:[Address]");
		
		// GRID BAG LAYOUT
		gbLayout = new GridBagLayout();
		// GRID BAG CONSTRAINTS
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 1;
		c2.gridwidth = WIDTH;
		
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 2;
		c3.gridwidth = WIDTH;
		
		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 1;
		c4.gridy = 0;
		
		// MESSAGE OUTPUT
		messageOutput = new JTextArea();
		messageOutput.setEditable(false);
		messageOutput.setPreferredSize(new Dimension(WIDTH, 250));
		
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setTitle("Jadi Chat");
		this.setLayout(gbLayout);
		this.add(ipInput, c1);
		this.add(messageOutput, c2);
		this.add(username, c4);
		this.add(userInput, c3);
		this.pack();
		userInput.requestFocus();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(userInput.hasFocus() && userInput.getText() != ""){
				if(userInput.getText().equals("<sys> username")){
					userInput.setText("");
					//Do command :
					username.setEditable(true);
					//Output what happened:
					console("System > Username has been unlocked.");
					return;
				}
				if(userInput.getText().equals("<sys> invert")){
					userInput.setText("");
					//Do command :
					if(!colorInverted){
						messageOutput.setBackground(Color.BLACK);
						messageOutput.setForeground(Color.WHITE);
						userInput.setBackground(Color.BLACK);
						userInput.setForeground(Color.WHITE);
						colorInverted = true;
					}else{
						messageOutput.setBackground(Color.WHITE);
						messageOutput.setForeground(Color.BLACK);
						userInput.setBackground(Color.WHITE);
						userInput.setForeground(Color.BLACK);
						colorInverted = false;
					}
					//Output what happened:
					console("System > Color has been inverted");
					return;
				}
				if(userInput.getText().startsWith("<sys> lock ")){
					//Do command :
					String name = userInput.getText().substring(11);
					userInput.setText("");
					client.send(("/locked/" + name).getBytes());
					return;
				}
				if(userInput.getText().startsWith("<sys> restore ")){
					//Do command :
					String name = userInput.getText().substring(14);
					userInput.setText("");
					client.send(("/restore/" + name).getBytes());
					return;
				}
				send(name, userInput.getText());
				userInput.setText("");
			}else if(username.hasFocus() && username.getText() != ""){
				username.setForeground(new Color(0, 230, 0));
				name = username.getText();
				System.out.println("Name: " + name);
				username.setEditable(false);
				if(!ipInput.isEditable() && !address.equals("none") && port != -1  && !hasConnected){
					userInput.setText("");
					userInput.requestFocus();
					hasConnected = true;
					client = new Client(name, address, port);
					client.connect();
					userInput.setEditable(true);
					listen();
				}
			}else if(ipInput.hasFocus() && ipInput.getText() != ""){
				try{
					String[] addPort = ipInput.getText().split(":");
					if(addPort.length > 2)
						throw new Exception();
					if(addPort.length == 2){
						port = Integer.valueOf(addPort[1]);
					}else{
						port = 8192;
					}
					address = addPort[0];
					System.out.println("Port: " + port);
					System.out.println("Address: " + address);
					ipInput.setForeground(new Color(0, 230, 0));
					ipInput.setEditable(false);
				}catch(Exception exception){
					ipInput.setText("BAD IP");
					return;
				}
				if(!username.isEditable() && !name.equals("Unknown") && !hasConnected){
					userInput.setText("");
					userInput.requestFocus();
					hasConnected = true;
					client = new Client(name, address, port);
					client.connect();
					userInput.setEditable(true);
					listen();
				}
			}
		}
	}
	
	public void console(String message){
		if(messageOutput.getLineCount() < 13){
			messageOutput.setText(messageOutput.getText() + message + "\n");
		}else{
			messageOutput.setText(shaveFirstLine(messageOutput.getText()) + message + "\n");
		}
	}
	
	
	public void send(String n, String s){
		String message = n + " > " + s;
		message = "/m/" + message;
		client.send(message.getBytes());
	}
	
	public void listen(){
		listen = new Thread(){
			public void run(){
				while(hasConnected){
					String message = client.receive();
					if(message.startsWith("/c/")){
						client.setID(Integer.valueOf(message.substring(3).trim()));
						console("Successfully connected to server: " + client.getID());
					}
					if(message.startsWith("/m/")){
						console(message.substring(3));
					}
					if(message.startsWith("/locked/")){
						if(message.substring(8).trim().equals(name)){
							userInput.setForeground(Color.RED);
							userInput.setBackground(Color.BLACK);
							userInput.setText("YOU HAVE BEEN LOCKED OUT!");
							userInput.setEditable(false);
							userInput.setHorizontalAlignment(JTextField.CENTER);
							messageOutput.setBackground(Color.BLACK);
							messageOutput.setForeground(Color.BLACK);
						}else{
							console(message.substring(8).trim() + " has been Locked");
						}
					}
					if(message.startsWith("/restore/")){
						if(message.substring(9).trim().equals(name)){
							userInput.setForeground(Color.BLACK);
							userInput.setBackground(Color.WHITE);
							userInput.setText("");
							userInput.setEditable(true);
							userInput.setHorizontalAlignment(JTextField.LEFT);
							messageOutput.setBackground(Color.WHITE);
							messageOutput.setForeground(Color.BLACK);
						}else{
							console(message.substring(9).trim() + " has been Restored");
						}
					}
				}
			}
		};
		listen.start();
	}
	
	public String shaveFirstLine(String s){
		while(!s.startsWith("\n")){
			s = s.substring(1);
		}
		return s.substring(1);
	}
	
	public void focusGained(FocusEvent arg0) {
		if(username.hasFocus() && username.isEditable()){
			username.setText("");
		}else if(ipInput.hasFocus() && ipInput.isEditable() && ipInput.getText().equalsIgnoreCase("-- Enter IP --")){
			ipInput.setText("");
		}
	}
	
	public static void main(String[] args){
		ClientWindow clientW = new ClientWindow();
	}

	public void focusLost(FocusEvent arg0){}
	public void keyPressed(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}
