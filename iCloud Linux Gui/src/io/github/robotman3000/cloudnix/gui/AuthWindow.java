package io.github.robotman3000.cloudnix.gui;

import io.github.robotman3000.cloudnix.Credentials;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AuthWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;
	private JTextField txtUsernameApple;
	private JPasswordField pwdPassword;
	private JCheckBox chckbxNewCheckBox;

	/**
	 * Launch the window.
	 * @return 
	 */
	private static AuthWindow loadWindow() {
		try {
			AuthWindow dialog = new AuthWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(false);
			return dialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create the dialog.
	 */
	public AuthWindow() {
		setResizable(false);
		setBounds(100, 100, 184, 165);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtUsernameApple = new JTextField();
			txtUsernameApple.setBounds(12, 10, 150, 19);
			contentPanel.add(txtUsernameApple);
			txtUsernameApple.setColumns(10);
		}
		{
			pwdPassword = new JPasswordField();
			pwdPassword.setBounds(12, 38, 150, 19);
			contentPanel.add(pwdPassword);
		}
		
		chckbxNewCheckBox = new JCheckBox("Stay Logged In");
		chckbxNewCheckBox.setBounds(12, 65, 150, 23);
		contentPanel.add(chckbxNewCheckBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public static Credentials getCredentials(Thread thread) {
		final AuthWindow win = loadWindow();
		final Runnable okThread = new Runnable(){
			@Override
			public void run() {
				System.out.println("Logging In");
				win.dispose();
				
				//return new Credentials(win.txtUsernameApple.getText(), String.valueOf(win.pwdPassword.getPassword()), win.chckbxNewCheckBox.isSelected());
				
			}
		};
		win.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		win.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(okThread).start();
			}
		});
		win.setVisible(true);
		
		

		
		return null;
	}
}
