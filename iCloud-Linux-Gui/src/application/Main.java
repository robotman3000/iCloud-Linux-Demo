package application;

import icloud.Authenticator;
import icloud.CloudException;
import icloud.Credentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

	@FXML
	private WebView sessionPropertyViewGui;

	@FXML
	private ProgressBar programStatusBar;

	@FXML
	private TabPane sessionTabPane;

	@FXML
	private ListView<?> sessionListView;

	@FXML
	private TreeView<?> sessionPropertiesTreeGui;

	@FXML
	private Label programStatusLabel;

	private static String configPath = "iCloud_Linux_GUI.conf";
	private static Properties sysconfig = new Properties();

	private void log(String message) {
		System.out.println(">>> " + message);
	}

	private void err(String message) {
		System.err.println("!!! " + message);
	}

	private void tab(String prefix, String message) {
		System.out.println(prefix + "\t" + message);
	}

	private String readIn(String message) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		log(message);
		System.out.print("<<< ");
		String resp = in.nextLine();
		// in.close();
		return resp;
	}

	private boolean queryUserBoolean(String message) {
		String answer = queryUserString(message + " (yes/no)");
		if (answer.equalsIgnoreCase("yes")) {
			return true;
		}
		return false;
	}

	private String queryUserString(String message) {
		String input = readIn(message);
		return input;
	}


	@Override
	public void init() {
		log("Program Starting");

		if (!loadConfig()) {
			log("Attempting to load default config");
			sysconfig = getDefaultConfig();
		}
	}


	@Override
	public void start(Stage primaryStage) {
		log("Program Started");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main-Window.fxml"));
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
			// primaryStage.setFullScreen(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		log("Program Stopping");
		saveConfig();
	}

	private boolean loadConfig() {
		log("Attempting to load saved config");
		try {
			sysconfig = loadPropertiesFile(configPath);
			log("Comparing loaded config with default config");
			for (String key : getDefaultConfig().stringPropertyNames()) {
				if (!sysconfig.containsKey(key)) {
					log("Config option " + key
							+ " was not found in the loaded config file");
					log("Adding config option " + key
							+ " to config with default value");
					sysconfig.put(key, getDefaultConfig().get(key));
				}
			}
			return true;
		} catch (IOException e) {
			err("Failed to load config file from path: " + configPath);
			e.printStackTrace();
		}
		return false;
	}


	private Properties getDefaultConfig() {
		Properties defaultConfig = new Properties();
		defaultConfig.put("session.save.path", "sessions/");
		return defaultConfig;
	}


	private boolean saveConfig() {
		log("Attempting to save config to disk");
		try {
			savePropertiesFile(configPath, sysconfig);
			log("Successfully saved config to disk");
			return true;
		} catch (IOException e) {
			err("Failed to save config to disk");
			e.printStackTrace();
		}
		return false;
	}

	private Properties loadPropertiesFile(String fileName) throws IOException, FileNotFoundException {

		Properties prop = new Properties();
        File f = new File(fileName);
        InputStream inStream = new FileInputStream(f);
        prop.load(inStream);
        inStream.close();
		return prop;
	}

	private void savePropertiesFile(String fileName, Properties fileToSave) throws IOException {
		log(fileToSave.toString());
        File f = new File(fileName);
        OutputStream out = new FileOutputStream(f);
        fileToSave.store(out, "This is an optional header comment string");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	private void runAuthenticationThread(Task<Credentials> theTask) {
		Task<String> authenticationTask = new Task<String>(){
			@Override
			protected String call() throws Exception {
				String sessionKey = "";
				try {
					Credentials authInfo = theTask.get();
					sessionKey = Authenticator.authenticate(authInfo);
					log("Signed in");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (CloudException e) {
					err("User Signin Failed");
					e.printStackTrace();
				}
				return sessionKey;
			}
		};
		new Thread(authenticationTask, "Session Authentication Thread").start();
		
	}
	
	@FXML
	void onCreateSession(ActionEvent event) {
		log("Creating new Session");
		AuthWindow authWin = new AuthWindow();
		Task<Credentials> theTask = new Task<Credentials>(){
			@Override
			protected Credentials call() throws Exception {
				System.out.println("Ran Callback");
				boolean extendedLogin = authWin.extendedLoginTick.isSelected();
				String password = authWin.passwordBox.getText();
				String username = authWin.usernameBox.getText();
				Credentials authKey = new Credentials(username, password, extendedLogin); // This throws an exception if the args are invalid
				// An exception would cause this task's state to be FAILED
				return authKey;
			}
		};
		authWin.setSigninCallback(theTask);
		
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Credentials-Maker.fxml"));
            loader.setController(authWin);
            loader.setRoot(authWin.root);
            loader.load();
            Scene scene = new Scene(authWin.root);
			Stage authStage = new Stage();
			authStage.setScene(scene);
			
			theTask.stateProperty().addListener(new ChangeListener<State>(){ // This must be before "authStage.show()" so that it will certainly be registered when the window is shown
				@Override
				public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
					if(newValue.toString().equalsIgnoreCase("FAILED") || newValue.toString().equalsIgnoreCase("SUCCEEDED")){
						authStage.close();
						runAuthenticationThread(theTask);
					}
				}
			});
			
			authStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onDeleteSession(ActionEvent event) {
		log("Deleting Session");
	}

	@FXML
	void onSaveSession(ActionEvent event) {
		log("Saving Session");
	}

	@FXML
	void onLoadSession(ActionEvent event) {
		log("Loading Session");
	}

	@FXML
	void sessionListItemDropped(ActionEvent event) {

	}

	@FXML
	void sessionListEditCancel(ActionEvent event) {

	}

	@FXML
	void sessionListEditCommit(ActionEvent event) {

	}

	@FXML
	void sessionListEditStart(ActionEvent event) {

	}
}
