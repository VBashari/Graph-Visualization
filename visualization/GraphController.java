package visualizationREDO;

import java.util.Random;

import implementation.Graph;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GraphController {
	private Graph<String> graph;
	private Random rand;
	
	private Pane pane;
	private Group nodes, edges;
	
	public GraphController() {
		rand = new Random();
		graph = new Graph<>(false);
		
		nodes = new Group();
		edges = new Group();
		edges.setStyle("-fx-border-color: black");

		pane = new Pane(nodes, edges);
		pane.setPrefSize(500, 500);
	}
	
	public Pane getPane() {
		return pane;
	}
	
	public ObservableList<Node> getNodes() {
		return nodes.getChildrenUnmodifiable();
	}
	
	public void addNode(String value) {
		// Value validation
		if(value.isBlank())
			throw new IllegalArgumentException("Node value cannot be empty");
		
		if(graph.addNode(value)) {
			NodeDisplay node = new NodeDisplay(value);
			nodes.getChildren().add(node);

			node.relocate(rand.nextDouble() * 500, rand.nextDouble() * 500);
			
			// Remove node by right clicking on it
			node.setOnMouseClicked(e -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					e.consume();
					nodes.getChildren().remove(node);
					//TODO remove edges
				}
			});
			
			// Add drag-and-drop feature
			node.setOnMouseDragged(e -> {
				node.relocate(e.getSceneX(), e.getSceneY());
			});
		}
	}
	
	public void addEdge(String weight, int startNodeIndex, int endNodeIndex) {
		// Value validation
		if(startNodeIndex < -1 || startNodeIndex >= nodes.getChildren().size())
			throw new IndexOutOfBoundsException("Out of bounds start node value");
		else if(endNodeIndex < -1 || endNodeIndex >= nodes.getChildren().size())
			throw new IndexOutOfBoundsException("Out of bounds start node value");
		
		try {
			double weightValue = weight.isBlank() ? 0 : Double.parseDouble(weight);
			NodeDisplay start = (NodeDisplay) nodes.getChildren().get(startNodeIndex), end = (NodeDisplay) nodes.getChildren().get(endNodeIndex);
			
			if(graph.addEdge(start.toString(), end.toString(), weightValue)) {				
				Line line = new Line();
				line.setStartX(0.0f);
				line.setStartY(0.0f);
				line.setEndX(100.0f);
				line.setEndY(100.0f);
				
				line.startXProperty().bind(start.centerXProperty());
				line.startYProperty().bind(start.centerYProperty());
				
				line.endXProperty().bind(end.centerXProperty());
				line.endYProperty().bind(end.centerYProperty());
				
				edges.getChildren().addAll(line, new Circle());
				System.out.println(start);
//				EdgeDisplay edge = new EdgeDisplay(
//					(NodeDisplay) nodes.getChildren().get(startNodeIndex),
//					(NodeDisplay) nodes.getChildren().get(endNodeIndex),
//					weightValue
//				);
//				
//				edge.getLine().startXProperty().bind(start.centerXProperty());
//				edge.getLine().startYProperty().bind(start.centerYProperty());
//				
//				edge.getLine().endXProperty().bind(end.centerXProperty());
//				edge.getLine().endYProperty().bind(end.centerYProperty());
//				
//				edges.getChildren().add(edge);
			}
			
			
			for(Node x: edges.getChildren())
				System.out.println(x);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Weight has to be of numeric value");
		}
	}
}