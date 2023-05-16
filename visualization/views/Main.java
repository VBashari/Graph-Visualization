package visualization.views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Graph visualization");
		primaryStage.setScene(new Scene(new CustomPane()));
		primaryStage.show();
	}
	
}
