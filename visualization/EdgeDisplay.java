package visualizationREDO;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class EdgeDisplay extends Group {
	private Line line;
	private Text weight;
	private Polygon directionTriangle;
	
	public EdgeDisplay(NodeDisplay start, NodeDisplay end, double weight) {
		line = new Line();
		this.weight = new Text(String.valueOf(weight));
//		directionTriangle = new Polygon();

		// Binding line to nodes
//		line.startXProperty().bind(start.centerXProperty());
//		line.startYProperty().bind(start.centerYProperty());
//		
//		line.endXProperty().bind(end.centerXProperty());
//		line.endYProperty().bind(end.centerYProperty());
		
		// Binding weight text to line
		this.weight.xProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
		this.weight.yProperty().bind(line.startYProperty().add(line.endYProperty().divide(2)));
		
		// Setting initial points of the triangle
//		directionTriangle.getPoints().addAll(new Double[] {
//			end.centerXProperty().doubleValue(), end.centerYProperty().doubleValue(), // head on triangle
//			line.endXProperty().subtract(10).doubleValue(), line.endYProperty().subtract(10).doubleValue(), // left side
//			line.endXProperty().add(10).doubleValue(), line.endYProperty().add(10).doubleValue(), // right side
//		});

		// TODO Binding triangle base points to end of line, and the top/anchor point to end nodes
//		line.startXProperty().addListener((obs, oldValue, newValue) -> {
//			directionTriangle.getPoints().set(0, newValue.doubleValue());
//		});
		
		getChildren().addAll(line, this.weight);
	}
	
	public Line getLine() {
		return line;
	}
}
