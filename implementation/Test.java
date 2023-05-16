package implementation;

public class Test {
	public static void main(String[] args) {
		Graph<String> g = new Graph<>(false);
		
		g.addNode("A");
		g.addNode("B");
		g.addNode("C");
		g.addNode("D");
		g.addNode("E");
		g.addNode("F");
		
//		g.addEdge("A", "B",-12);
//		g.addEdge("A","F", -8);
//		g.addEdge("B","C");
//		g.addEdge("D","E",-1);
//		g.addEdge("D","F",-5);
//		g.addEdge("E","F",-3);
		
		g.addEdge("A", "B",12);
		g.addEdge("A","F", -8);
		g.addEdge("B","C");
		g.addEdge("D","E",1);
		g.addEdge("D","F",5);
		g.addEdge("E","F",3);
		
		g.bfs("C");
		g.dfs("C");
		
		System.out.println(g);
		
		System.out.println(g.shortestPath("B", "F"));
		
		g.removeNode("B");
		System.out.println("\n" + g + "\n");
		
//		g.bfs(3);
//		g.dfs(3);
	}
}
