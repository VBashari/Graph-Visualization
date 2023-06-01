package visualization;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class EdgeDisplay extends Group {
	private Line line;
	private Text weight;
	
	private Polygon directionTriangle;
	private DoubleBinding triangleX, triangleY;
	
	private NodeDisplay start, end;
	
	public EdgeDisplay(NodeDisplay start, NodeDisplay end, double weight) {
		this.start = start;
		this.end = end;
		
		line = new Line();
		this.weight = new Text(String.valueOf(weight));

		// Setting line style
		line.setStrokeWidth(5);
		line.setStroke(Color.LIGHTGRAY);
		
		// Binding line to nodes
		line.startXProperty().bind(start.layoutXProperty());
		line.startYProperty().bind(start.layoutYProperty());
		
		line.endXProperty().bind(end.layoutXProperty());
		line.endYProperty().bind(end.layoutYProperty());
		
		// Binding weight text to line
		this.weight.xProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
		this.weight.yProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
		
		
		// triangle
		Rectangle rec = new Rectangle(15,15,Color.LIGHTGRAY);
		rec.xProperty().bind(line.endXProperty());
		rec.yProperty().bind(line.endYProperty());
		
//		triangleX = line.endXProperty().add(line.startXProperty().negate());
//		triangleY = line.endYProperty().add(line.startYProperty().negate());
//
//		directionTriangle = new Polygon(line.getEndX(), line.getEndY(), line.getEndX() - 10, line.getEndY() + 8, line.getEndX() - 10, line.getEndY() - 8);
//		directionTriangle.setFill(Color.LIGHTGRAY);
//		Rotate rotate = new Rotate(0, 0, 0, 1, Rotate.Z_AXIS);
//		directionTriangle.getTransforms().add(rotate);
//		
//		triangleX.addListener((obs, oldValue, newValue) -> rotate.setAngle(getAngle(triangleY.doubleValue(), newValue.doubleValue())));
//		triangleY.addListener((obs, oldValue, newValue) -> rotate.setAngle(getAngle(newValue.doubleValue(), triangleX.doubleValue())));
//		
//		directionTriangle.layoutXProperty().bind(line.endXProperty());
//		directionTriangle.layoutYProperty().bind(line.endYProperty());
		
		// Setting initial points of the triangle
//		directionTriangle.getPoints().addAll(new Double[] {
//			line.endXProperty(), line.endYProperty() // head on triangle
//			line.endXProperty().subtract(10).doubleValue(), line.endYProperty().subtract(10).doubleValue(), // left side
//			line.endXProperty().add(10).doubleValue(), line.endYProperty().add(10).doubleValue(), // right side
//		});
		

		// TODO Binding triangle base points to end of line, and the top/anchor point to end nodes
//		line.startXProperty().addListener((obs, oldValue, newValue) -> {
//			directionTriangle.getPoints().set(0, newValue.doubleValue());
//		});
		
		getChildren().addAll(line, this.weight, rec);
	}
	
	public NodeDisplay getStartNode() {
		return start;
	}
	
	public NodeDisplay getEndNode() {
		return end;
	}
	
	private double getAngle(double x, double y) {
		return Math.toDegrees(Math.atan2(y, x));
	}
}
