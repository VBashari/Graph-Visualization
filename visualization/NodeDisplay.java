package visualizationREDO;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NodeDisplay extends Group {
	private Circle shape;
	private Text value;
	
	public NodeDisplay(String value) {
		shape = new Circle(15);
		this.value = new Text(value);
		
		this.value.xProperty().bind(shape.centerXProperty().subtract(20));
		this.value.yProperty().bind(shape.centerYProperty().subtract(20));
		
		getChildren().addAll(shape, this.value);
	}

	@Override
	public String toString() {
		return value.getText();
	}
}
