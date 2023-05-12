package visualization.controllers;

import java.util.Random;

import implementation.Graph;
import javafx.collections.ObservableList;
import javafx.scene.Group;
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
			nodes.getChildren().add(new NodeDisplay(rand.nextDouble() * 500, rand.nextDouble() * 500, label));
			edgeOptions.add(label);
		}
	}
	
	public void addEdge(int start, int end, String weight, Group edges) {
//		if()
	}
}
