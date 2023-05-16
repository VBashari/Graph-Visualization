package visualization.controllers;

import java.util.Random;

import implementation.Graph;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import visualization.models.EdgeDisplay;
import visualization.models.NodeDisplay;

public class GraphController {
	private Random rand;
	private Graph<String> graph;
	
	public GraphController() {
		rand = new Random();
		graph = new Graph<>();
	}
	
	public void addNode(String label, Group nodes, ObservableList<String> edgeOptions) {
		if(label.isBlank())
			throw new IllegalArgumentException("Node label cannot be empty");
		
		if(graph.addNode(label)) {
			nodes.getChildren().add(new NodeDisplay(rand.nextDouble() * 500 + 50, rand.nextDouble() * 500 + 50, label));
			edgeOptions.add(label);
		}
	}
	
	public void addEdge(int start, int end, String weight, Group nodes, Group edges) {
		if(start == -1 || end == -1)
			throw new IllegalArgumentException("You have to select a starting and ending node");
		
		if(weight.isBlank())
			throw new IllegalArgumentException("You have to enter a weight for the edge");
		
		try {
			double weightNr = Double.parseDouble(weight);
			
			if(graph.addEdge(start, end, weightNr))
				edges.getChildren().add(new EdgeDisplay((NodeDisplay) nodes.getChildren().get(start), 
														(NodeDisplay) nodes.getChildren().get(end), 
														weightNr));
		} catch(NumberFormatException ex) {
			throw ex;
		}
	}
}
