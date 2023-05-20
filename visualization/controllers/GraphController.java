package visualization.controllers;

import java.util.Random;

import implementation.Graph;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import visualization.models.EdgeDisplay;
import visualization.models.NodeDisplay;

public class GraphController {
	private Random rand;
	private Graph<String> graph;
	
	public GraphController() {
		rand = new Random();
		graph = new Graph<>(false);
	}
	
	public String addNode(String label, ObservableList<Node> nodes) {
		if(label.isBlank())
			throw new IllegalArgumentException("Node label cannot be empty");
		
		if(graph.addNode(label)) {
			NodeDisplay node = new NodeDisplay(rand.nextDouble() * 500 + 50, rand.nextDouble() * 500 + 50, label);
			nodes.add(node);
			
			node.setOnMouseClicked(e -> {
				if(e.getButton() == MouseButton.SECONDARY)
					nodes.remove(node);
			});
			
			return label;
		}
		
		return null;
	}
		
	public void addEdge(NodeDisplay start, NodeDisplay end, String weight, ObservableList<Node> edges) {
		if(weight.isBlank() || start.value().equals(end.value()))
			throw new IllegalArgumentException();

		try {
			double weightNr = Double.parseDouble(weight);
			
			if(graph.addEdge(start.value(), end.value(), weightNr)) {
				if(!graph.isDirected())
					edges.add(new EdgeDisplay(end, start, weightNr));

				edges.add(new EdgeDisplay(start, end, weightNr));
			}
		} catch(NumberFormatException ex) {
			throw ex;
		}
	}
	
	public void removeNode(String label) {
		if(graph.removeNode(label));
	}
}
