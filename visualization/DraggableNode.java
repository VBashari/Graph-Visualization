package visualization;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class DraggableNode {
	private double dragX, dragY;
	
	public void makeDraggable(Node node) {
//		node.setOnMousePressed(e -> {
//			dragX = e.getSceneX() - node.getTranslateX();
//			dragY = e.getSceneY() - node.getTranslateY();
//		});
//		
//		node.setOnMouseDragged(e -> {
//			System.out.println(node.getParent().getLayoutBounds());
//		});
		
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
				node.setLayoutX(localMouse.getX() - dragX);
			
			if(newY > 0 && newY < node.getParent().getLayoutBounds().getMaxY())
				node.setLayoutY(localMouse.getY() - dragY);
		});
	}
}
