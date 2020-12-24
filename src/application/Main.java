package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/MainView.fxml"));
			Scene scene = new Scene(root, 400, 400);
			Parent parent = loader.load();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Scene mainScene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Aplicação JavaFX");
			primaryStage.show();
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
