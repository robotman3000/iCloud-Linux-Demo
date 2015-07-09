package application;

import icloud.Credentials;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AuthWindow {

    @FXML
    protected CheckBox extendedLoginTick;

    @FXML
    protected PasswordField passwordBox;

    @FXML
    protected TextField usernameBox;
    protected GridPane root = new GridPane();

	private Task<Credentials> signinCallback = new Task<Credentials>(){
		@Override
		protected Credentials call() throws Exception {
			System.err.println("Using default callback");
			return null;
		}
	};

	@FXML
    void signinActivated(ActionEvent event) {
    	new Thread(signinCallback, "Signin Thread").start();
    }
	
	public void setSigninCallback(Task<Credentials> signinCallback){
		this.signinCallback = signinCallback;
	}
}
