package icloud.gui;

import icloud.user.UserSession;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AuthWindow extends JDialog {
	private JTextField UserNameBox;
	private JPasswordField passwordField;
	private JCheckBox chckbxExtendedlogin;
	protected boolean signedIn = false;
	private boolean extendedLogin;
	private String username;
	private String password;

	/**
	 * Create the dialog.
	 */
	public AuthWindow() {
		setBounds(100, 100, 260, 129);
		getContentPane().setLayout(null);
		
		UserNameBox = new JTextField();
		UserNameBox.setBounds(111, 10, 136, 19);
		getContentPane().add(UserNameBox);
		UserNameBox.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(111, 41, 136, 19);
		getContentPane().add(passwordField);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(12, 12, 81, 15);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 43, 70, 15);
		getContentPane().add(lblPassword);
		
		chckbxExtendedlogin = new JCheckBox("Extended Login");
		chckbxExtendedlogin.setBounds(12, 68, 136, 23);
		getContentPane().add(chckbxExtendedlogin);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validCredentials()){
					
					signedIn = true;
				}
			}
		});
		btnSignIn.setBounds(156, 67, 91, 25);
		getContentPane().add(btnSignIn);

	}

	protected boolean validCredentials() {
		if(this.UserNameBox.getText() != null && this.passwordField.getPassword() != null ){
			password = this.passwordField.getPassword().toString();
			username = this.UserNameBox.getText();
			extendedLogin = this.chckbxExtendedlogin.isSelected();
			return true;
		}
		return false;
	}

	public UserSession createUser() {
		UserSession user;
		
		synchronized(this){
			while(!signedIn){
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Waiting for User Credentials...");
			}
			user = new UserSession(username, password, extendedLogin);
		}
		
		return user;
	}
}
