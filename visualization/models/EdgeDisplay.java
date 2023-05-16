package visualization.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class EdgeDisplay extends Pane {
//	private NodeDisplay start, end;
	private Text weightTxt;
	private Line line;
	
	public EdgeDisplay(NodeDisplay start, NodeDisplay end, double weight) {		
		line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
		
		//TODO set center of text to center of line
		weightTxt = new Text((line.getStartX() + line.getEndX()) / 2, 
							(line.getStartY() + line.getEndY()) / 2, 
							Double.toString(weight));
		//TODO put in style sheet
		weightTxt.setFont(Font.font(null, FontWeight.BOLD, 25));		
		
		getChildren().addAll(line, weightTxt);
	}
}
