package visualization.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class EdgeDisplay extends Pane {
//	private NodeDisplay start, end;
	private Text weightTxt;
	private Line line;
	
	public EdgeDisplay(NodeDisplay start, NodeDisplay end, double weight) {		
		line = new Line(start.getLayoutX(), start.getLayoutY(), end.getLayoutX(), end.getLayoutY());
		weightTxt = new Text((line.getStartX() + line.getEndX()) / 2, 
							(line.getStartY() + line.getEndY()) / 2, 
							Double.toString(weight));
		
		getChildren().addAll(line, weightTxt);
	}
}
