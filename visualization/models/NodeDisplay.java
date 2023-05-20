package visualization.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NodeDisplay extends Pane {
	private static int RADIUS = 5;
	private Circle view;
	private Text value;
	
	public NodeDisplay(double xPosition, double yPosition, String value) {
		//TODO make bigger, both
		this.view = new Circle(RADIUS);
		this.value = new Text(view.getCenterX() - 10, view.getCenterY() -10, value);
		
		
		
		getChildren().addAll(this.view, this.value);
		setStyle("-fx-background-color: gray"); 
		this.view.setStyle("-fx-background-color: red");
		this.value.setStyle("-fx-background-color: red");
	}
	
//	public double getX() {
//		return view.getCenterX();
//	}
//	
//	public double getY() {
//		return view.getCenterY();
//	}
	
	public String value() {
		return value.getText();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof NodeDisplay)
			return ((NodeDisplay) o).getLayoutX() == getLayoutX() && ((NodeDisplay) o).getLayoutY() == getLayoutY();
		
		return false;
	}
}
