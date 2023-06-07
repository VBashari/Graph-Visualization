package visualization.models;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class EdgeDisplay extends Group {
	private Line line;
	private Text weight;
	
	private Polygon directionTriangle;
	private DoubleBinding x, y;
	
	private NodeDisplay start, end;
	
	public EdgeDisplay(NodeDisplay start, NodeDisplay end, double weight, boolean isDirected) {
		this.start = start;
		this.end = end;

		// Create line & set style
		line = new Line();
		
		line.setStrokeWidth(5);
		line.setStroke(Color.web("#B4B8C5"));
		
		// Binding line to nodes
		line.startXProperty().bind(start.layoutXProperty());
		line.startYProperty().bind(start.layoutYProperty());
		
		line.endXProperty().bind(end.layoutXProperty());
		line.endYProperty().bind(end.layoutYProperty());
		
		// Create & bind weight text to line
		this.weight = new Text(String.valueOf(weight));
		this.weight.xProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
		this.weight.yProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
		
		getChildren().addAll(line, this.weight);
		
		if(isDirected)
			createDirectionTriangle();
	}
	
	public void setUndirected() {
		if(getChildren().size() > 2)
			getChildren().remove(2);
	}
	
	public void setDirected() {
		if(getChildren().size() <= 2) {
			if(directionTriangle == null)
				createDirectionTriangle();
			else
				getChildren().add(directionTriangle);
		}
	}
	
	private void createDirectionTriangle() {
		directionTriangle = new Polygon(line.getEndX(), line.getEndY(), line.getEndX() - 30, line.getEndY() + 8, line.getEndX() - 30, line.getEndY() - 8);
		directionTriangle.setFill(Color.web("#B4B8C5"));
		
		// Bind endpoints of triangle to line
		line.endXProperty().addListener(e -> {
			directionTriangle.getPoints().set(0, line.getEndX());
			directionTriangle.getPoints().set(2, line.getEndX()-30);
			directionTriangle.getPoints().set(4, line.getEndX()-30);
		});
		
		line.endYProperty().addListener(e -> {
			directionTriangle.getPoints().set(1, line.getEndY());
			directionTriangle.getPoints().set(3, line.getEndY() + 8);
			directionTriangle.getPoints().set(5, line.getEndY() - 8);
		});
		
		// Set triangle to rotate with line
		x = line.endXProperty().add(line.startXProperty().negate());
		y = line.endYProperty().add(line.startYProperty().negate());
		
		Rotate rotate = new Rotate(0,0,0);
		rotate.pivotXProperty().bind(line.endXProperty());
		rotate.pivotYProperty().bind(line.endYProperty());
		
		x.addListener((obs, oldValue, newValue) -> rotate.setAngle(getAngle(y.doubleValue(), newValue.doubleValue())));
		y.addListener((obs, oldValue, newValue) -> rotate.setAngle((getAngle(newValue.doubleValue(), x.doubleValue()))));
		
		directionTriangle.getTransforms().add(rotate);
		getChildren().add(directionTriangle);
	}
	
	private double getAngle(double dy ,double dx){
	    return Math.toDegrees(Math.atan2(dy, dx));
	}
	
	public NodeDisplay getStartNode() {
		return start;
	}
	
	public NodeDisplay getEndNode() {
		return end;
	}
}
