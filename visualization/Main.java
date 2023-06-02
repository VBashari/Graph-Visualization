package visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GraphView view = new GraphView();
		
		primaryStage.setTitle("Graph visualization");
		primaryStage.setScene(new Scene(view));
		primaryStage.show();
	}
	
}
