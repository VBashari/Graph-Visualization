package visualization.models;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class EdgeDisplay extends Group {
	private Line line;
	private Text weight;
	
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
		
		getChildren().addAll(line, this.weight);
	}
	
	public NodeDisplay getStartNode() {
		return start;
	}
	
	public NodeDisplay getEndNode() {
		return end;
	}
}
