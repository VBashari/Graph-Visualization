package visualization.models;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class DraggableNode {
	private double dragX, dragY;
	
	public void makeDraggable(Node node) {
		node.setOnMousePressed(e -> {
			Point2D localMouse = node.localToParent(e.getX(), e.getY());
			dragX = node.getLayoutX() - localMouse.getX();
			dragY = node.getLayoutY() - localMouse.getY();
			
			e.consume();
		});
		
		node.setOnMouseDragged(e -> {
			Point2D localMouse = node.localToParent(e.getX(), e.getY());			
			double newX = localMouse.getX() - dragX, newY = localMouse.getY() - dragY;
			
			if(newX > 0 && newX < node.getParent().getLayoutBounds().getMaxX())
				node.setLayoutX(newX);
			
			if(newY > 0 && newY < node.getParent().getLayoutBounds().getMaxY())
				node.setLayoutY(newY);
		});
	}
}
