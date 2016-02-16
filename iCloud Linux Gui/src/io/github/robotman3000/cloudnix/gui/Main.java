package io.github.robotman3000.cloudnix.gui;

import io.github.robotman3000.cloudnix.Authenticator;
import io.github.robotman3000.cloudnix.CloudException;
import io.github.robotman3000.cloudnix.Credentials;
import io.github.robotman3000.cloudnix.NoteCreateRequest;
import io.github.robotman3000.cloudnix.CloudSessionManager;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length < 3){
			System.out.println("Invalid Arguments Provided");
			System.exit(1);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			//SessionManager.getInstance().getSessionReader(authId);
			String sessionId = Authenticator.authenticate(new Credentials(args[0], args[1], Boolean.parseBoolean(args[2])));
			CloudSessionManager.executeRequest(sessionId, new NoteCreateRequest());
			CloudSessionManager.getInstance().getNoteAppSession(sessionId);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
