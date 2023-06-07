package visualization;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import implementation.Graph;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import visualization.models.DraggableNode;
import visualization.models.EdgeDisplay;
import visualization.models.NodeDisplay;

public class GraphController {
	private Graph<NodeDisplay> graph;
	private Random rand;
	
	private Pane pane;
	private Group nodes, edges;
	
	public GraphController() {
		rand = new Random();
		graph = new Graph<>(false);
		
		nodes = new Group();
		edges = new Group();
		
		pane = new Pane(edges, nodes);
		pane.setPrefSize(500, 500);
	}
	
	public Pane getPane() {
		return pane;
	}
	
	public ObservableList<Node> getNodes() {
		return nodes.getChildrenUnmodifiable();
	}
	
	public void setDirected() {
		graph.setIsDirected(true);
		
		for(Node edge: edges.getChildren())
			((EdgeDisplay) edge).setDirected();
	}
	
	public void setUndirected() {
		graph.setIsDirected(false);
		
		for(Node edge: edges.getChildren())
			((EdgeDisplay) edge).setUndirected();
	}
	
	public void addNode(String value) {
		// Value validation
		if(value.isBlank())
			throw new IllegalArgumentException("Node value cannot be empty");
		
		NodeDisplay node = new NodeDisplay(value);
		
		if(graph.addNode(node)) {
			nodes.getChildren().add(node);
			node.relocate(rand.nextDouble() * node.getParent().getLayoutBounds().getMaxX(), rand.nextDouble() * node.getParent().getLayoutBounds().getMaxY());
			
			// Remove node by right-clicking on it
			node.setOnMouseClicked(e -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					e.consume();
					// Remove edges
					if(graph.removeNode(node)) {
						nodes.getChildren().remove(node);
						edges.getChildren().removeIf(x -> ((EdgeDisplay) x).getStartNode().equals(node) || ((EdgeDisplay) x).getEndNode().equals(node));
					}
				}
			});
			
			// Add drag-and-drop feature
			DraggableNode delta = new DraggableNode();
			delta.makeDraggable(node);
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
			
			if(graph.addEdge(start, end, weightValue)) {
				if(!graph.isDirected())
					addEdge(end, start, weightValue, graph.isDirected());
				
				addEdge(start, end, weightValue, graph.isDirected());
			}
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Weight has to be of numeric value");
		}
	}
	
	public List<NodeDisplay> bfs(int nodeIndex) throws Exception {
		//	Value validation
		if(nodeIndex < -1 || nodeIndex >= nodes.getChildren().size())
			throw new IndexOutOfBoundsException("Out of bounds node value");
		
		List<NodeDisplay> result = Collections.unmodifiableList(graph.bfs(((NodeDisplay) nodes.getChildren().get(nodeIndex))));
		
		if(result == null)
			throw new Exception("No connection between the nodes");
		
		return result;
	}
	
	public List<NodeDisplay> dfs(int nodeIndex) throws Exception {
		// Value validation
		if(nodeIndex < -1 || nodeIndex >= nodes.getChildren().size())
			throw new IndexOutOfBoundsException("Out of bounds node value");
		
		List<NodeDisplay> result = Collections.unmodifiableList(graph.dfs(((NodeDisplay) nodes.getChildren().get(nodeIndex))));
		
		if(result == null)
			throw new Exception("No connection between the nodes");
		
		return result;
	}
	
	public List<NodeDisplay> shortestPath(int startNodeIndex, int endNodeIndex) throws Exception {
		// Value validation
		if(startNodeIndex < -1 || startNodeIndex >= nodes.getChildren().size())
			throw new IndexOutOfBoundsException("Out of bounds start node value");
		else if(endNodeIndex < -1 || endNodeIndex >= nodes.getChildren().size())
			throw new IndexOutOfBoundsException("Out of bounds start node value");
		
		try {
			return Collections.unmodifiableList(graph.shortestPath((NodeDisplay) nodes.getChildren().get(startNodeIndex),
																						(NodeDisplay) nodes.getChildren().get(endNodeIndex)));
		} catch(NullPointerException ex) {
			throw new Exception("No connection between these nodes");
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	private void addEdge(NodeDisplay start, NodeDisplay end, double weight, boolean isDirected) {
		EdgeDisplay edge = new EdgeDisplay(start, end, weight, isDirected);
			
		// Remove edge by right-clicking on it
		edge.setOnMouseClicked(e -> {
			if(e.getButton() == MouseButton.SECONDARY) {
				e.consume();
				edges.getChildren().remove(edge);
				
				if(!graph.isDirected())
					edges.getChildren().removeIf(x -> ((EdgeDisplay) x).getStartNode().equals(edge.getEndNode()) || ((EdgeDisplay) x).getEndNode().equals(edge.getStartNode()));
			}
		});
			
		edges.getChildren().add(edge);
	}
}
