package implementation;

public class Test {
	public static void main(String[] args) {
		Graph<Character> g = new Graph<>(false);
		
		g.addNodes(new Character[] {'a', 'b', 'c', 'd', 'e', 'f'});
		g.addEdge('a', 'b');
		g.addEdge('a', 'c', 4);
		g.addEdge('b','d',7);
		g.addEdge('b','f', 2);
		g.addEdge('f', 'e', 8);
		
		System.out.println(g.dfs('a').toString());
	}
}
