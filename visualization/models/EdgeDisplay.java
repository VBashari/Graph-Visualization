package visualization.models;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class EdgeDisplay extends Pane implements EventHandler<MouseEvent> {
	private Text txtWeight;
	private Line line;
	
	public EdgeDisplay(NodeDisplay start, NodeDisplay end, double weight) {
		line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
		
		//TODO set centre of text to centre of line
		txtWeight = new Text((line.getStartX() + line.getEndX()) / 2, 
							(line.getStartY() + line.getEndY()) / 2,
							Double.toString(weight));
		
		//TODO put in style sheet
		txtWeight.setFont(Font.font(null, FontWeight.BOLD, 25));		
		
		getChildren().addAll(line, txtWeight);
	}

	@Override
	public void handle(MouseEvent click) {
		if(click.getButton() == MouseButton.SECONDARY)
			((Group) this.getParent()).getChildren().remove(this);
	}
}
