package visualization.views;

import java.util.Random;

import implementation.Graph;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import visualization.controllers.GraphController;
import visualization.models.NodeDisplay;

public class GraphPane extends Pane {
	private GraphController controller = new GraphController();
	
//	private Random rand = new Random();
//	private Graph<String> graph = new Graph<>();
	
	private Group nodes = new Group();
	private Group edges = new Group();
	
	public GraphPane() {		
		getChildren().addAll(nodes, edges);
	}
	
//	public void addNode(String label) {
//		if(graph.addNode(label))
//			nodes.getChildren().add(new NodeDisplay(rand.nextDouble() * 500, rand.nextDouble() * 500, label));
//	}
	
	
//	public void addEdge(int x, int y, double weight) {
//		if(graph.addEdge(x, y, weight))
//			edges.getChildren().add(new EdgeDisplay((NodeDisplay) nodes.getChildren().get(x), 
//													(NodeDisplay) nodes.getChildren().get(y), 
//													weight));
//	}
}
