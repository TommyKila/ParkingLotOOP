package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogIn implements Initializable  {

	private double x, y;

	@FXML
	private Button loginButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Label wrongLogIn;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;

	public void newScene(Parent root, Stage stage, Scene scene) {
		root.setOnMousePressed((MouseEvent event) -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});

		root.setOnMouseDragged((MouseEvent event) -> {
			stage.setX(event.getScreenX() - x);
			stage.setY(event.getScreenY() - y);
			stage.setOpacity(0.8);
		});

		root.setOnMouseReleased((MouseEvent event) -> {
			stage.setOpacity(1);
		});

		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
	}

	public void cancelButtonClick(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void userLogIn(ActionEvent event) throws IOException {
		if (username.getText().isBlank() == false && password.getText().isBlank() == false) {
			checkLogin();
		} else {
			wrongLogIn.setText("Vui lòng nhập username hoặc mật khẩu.");
		}
	}

	private void checkLogin() throws IOException {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDatabase = connectNow.getConnection();

		String verifyLogin = "SELECT COUNT(1) FROM EMPLOYEE WHERE EMP_USERNAME = '" + username.getText()
				+ "' AND EMP_PASSWORD = '" + password.getText() + "'";
		String accountType = "SELECT EMP_POS FROM EMPLOYEE WHERE EMP_USERNAME = '" + username.getText() + "'";

		try {

			Statement verifyStatement = connectDatabase.createStatement();
			Statement typeStatement = connectDatabase.createStatement();
			ResultSet loginResult = verifyStatement.executeQuery(verifyLogin);
			ResultSet typeResult = typeStatement.executeQuery(accountType);

			while (loginResult.next()) {
				if (loginResult.getInt(1) == 1) {

					Stage loginForm = (Stage) cancelButton.getScene().getWindow();
					loginForm.close();

					while (typeResult.next()) {
						getData.username = username.getText();
						switch (typeResult.getString(1)) {
						case "ADMIN":
							Parent admin = FXMLLoader.load(getClass().getResource("Admin.fxml"));
							Stage adminStage = new Stage();
							Scene adminScene = new Scene(admin);

							newScene(admin, adminStage, adminScene);
							break;

						case "ATTENDANT":
							Parent attendant = FXMLLoader.load(getClass().getResource("Attendant.fxml"));
							Stage attendantStage = new Stage();
							Scene attendantScene = new Scene(attendant);

							newScene(attendant, attendantStage, attendantScene);
							break;
						}
					}

				} else {
					wrongLogIn.setText("Sai username hoặc mật khẩu");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

}