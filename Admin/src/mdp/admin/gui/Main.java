package mdp.admin.gui;
	

import java.util.logging.Level;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import mdp.admin.logger.AdminLogger;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {			
			
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("AdminWindow.fxml"));
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
