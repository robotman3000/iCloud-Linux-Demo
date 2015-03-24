package icloud.gui;

import icloud.gui.notes.NoteEditor;
import icloud.services.account.AccountManager;
import icloud.user.UserSession;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;

	private UserSession user;

	private AccountManager accountManager = new AccountManager(true, true);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Main main = new Main();
		main.user = new UserSession(args[0], args[1], false);
		main.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnAccount = new JButton("Account");
		btnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showUserDetails();
			}
		});
		btnAccount.setBounds(12, 12, 117, 25);
		frame.getContentPane().add(btnAccount);

		JButton btnNotes = new JButton("Notes");
		btnNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadNotes();
			}
		});
		btnNotes.setBounds(12, 49, 117, 25);
		frame.getContentPane().add(btnNotes);

		JButton btnContacts = new JButton("Contacts");
		btnContacts.setBounds(12, 86, 117, 25);
		frame.getContentPane().add(btnContacts);

		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signOut();
			}
		});
		btnSignOut.setBounds(141, 49, 117, 25);
		frame.getContentPane().add(btnSignOut);

		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signIn();
			}
		});
		btnSignIn.setBounds(141, 12, 117, 25);
		frame.getContentPane().add(btnSignIn);
	}

	protected void showUserDetails() {
		
		
	}

	protected void loadNotes() {
		NoteEditor noteEditor = new NoteEditor(user);
	}

	protected void signOut() {
		try {
			accountManager.logout(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void signIn() {
		try {
			accountManager.login(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Invalid User!!");
			e.printStackTrace();
			// createUser();
		}
	}

	private synchronized void createUser() {
		AuthWindow auth = new AuthWindow();
		auth.setVisible(true);
		user = auth.createUser();
		auth.setVisible(false);
	}

}
