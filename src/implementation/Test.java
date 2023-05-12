package implementation;

public class Test {
	public static void main(String[] args) {
		Graph<String> g = new Graph<>();
		
		g.addNode("A");
		g.addNode("B");
		g.addNode("C");
		g.addNode("D");
		g.addNode("E");
		g.addNode("F");
		
		g.addEdge(0,1,12);
		g.addEdge(0,5, 8);
		g.addEdge(1,0,12);
		g.addEdge(1,2);
		g.addEdge(2,1,0);
		g.addEdge(3,4,1);
		g.addEdge(3,5,5);
		g.addEdge(4,3,4);
		g.addEdge(4,5,3);
		g.addEdge(5,0,8);
		g.addEdge(5,3,5);
		g.addEdge(5,4,3);
		
		g.bfs(3);
		g.dfs(3);
		
		System.out.println("\n" + g + "\n");
		
		try {
			System.out.println(g.minimumSpanningTree(4));
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
}
