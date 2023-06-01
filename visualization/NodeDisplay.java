package visualization;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class NodeDisplay extends Group implements Comparable<NodeDisplay> {
	private Circle shape;
	private Text value;
	
	public NodeDisplay(String value) {
		shape = new Circle(15);
		this.value = new Text(value);
		
		this.value.xProperty().bind(shape.centerXProperty().subtract(20));
		this.value.yProperty().bind(shape.centerYProperty().subtract(20));
		
		getChildren().addAll(shape, this.value);
	}
	
	public Shape getShape() {
		return shape;
	}

	public String getValue() {
		return value.getText();
	}

	@Override
	public int compareTo(NodeDisplay o) {
		return this.getValue().compareTo(o.getValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof NodeDisplay)
			return ((NodeDisplay) obj).getValue().equals(this.getValue());
		
		return false;
	}
}
