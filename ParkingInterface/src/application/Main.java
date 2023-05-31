package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	private static Stage stg;
	private double x = 0, y = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stg = primaryStage;
			primaryStage.setResizable(false);
			Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Parking Lot");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			root.setOnMousePressed((MouseEvent evt) ->{
				x = evt.getSceneX();
				y = evt.getSceneY();
			});
			
			root.setOnMouseDragged((MouseEvent evt) -> {
				primaryStage.setX(evt.getScreenX() - x);
				primaryStage.setY(evt.getScreenY() - y);
				
				primaryStage.setOpacity(0.8);
			});
			
			root.setOnMouseReleased((MouseEvent evt) -> {
				primaryStage.setOpacity(1);
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeScene(String fxml) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
