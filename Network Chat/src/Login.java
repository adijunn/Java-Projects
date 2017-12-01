import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSplitPane;
import java.awt.List;
import javax.swing.JTextPane;
import java.awt.TextArea;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


public class Login extends JFrame {
	private JTextField name;
	private JTextField ipaddress;
	private JTextField port;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 380);
		getContentPane().setLayout(null);
		
		name = new JTextField();
		name.setBounds(83, 165, 134, 28);
		getContentPane().add(name);
		name.setColumns(10);
		
		ipaddress = new JTextField();
		ipaddress.setBounds(83, 205, 134, 28);
		getContentPane().add(ipaddress);
		ipaddress.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 171, 73, 16);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("IP Address");
		lblPassword.setBounds(10, 211, 73, 16);
		getContentPane().add(lblPassword);
		
		port = new JTextField();
		port.setBounds(83, 245, 134, 28);
		getContentPane().add(port);
		port.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Port");
		lblNewLabel.setBounds(22, 251, 61, 16);
		getContentPane().add(lblNewLabel);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("login pressed");
				String n = name.getText();
				String i = ipaddress.getText();
				int p = Integer.parseInt(port.getText());
				login(n, i, p);
			}

			private void login(String name, String ipaddress, int port) {
				dispose();
				System.out.println(name + ", " + ipaddress + ", " + port);
				new Client(name, ipaddress, port);
			}
		});
		btnLogin.setBounds(91, 311, 117, 29);
		getContentPane().add(btnLogin);
		
		JLabel lblNewLabel_1 = new JLabel("  NETCHAT");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblNewLabel_1.setBounds(34, 30, 232, 88);
		getContentPane().add(lblNewLabel_1);
	}
}
