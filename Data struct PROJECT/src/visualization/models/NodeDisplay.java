package visualization.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NodeDisplay extends Pane {
	private static int RADIUS = 5;
	private Circle view;
	private Text label;
	
	public NodeDisplay(double xPosition, double yPosition, String label) {
		this.view = new Circle(xPosition, yPosition, RADIUS);
		this.label = new Text(xPosition - 10, yPosition -10, label);
		
		getChildren().addAll(this.view, this.label);
	}
}
