package implementation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Graph<E extends Comparable<E>> {
	//TODO make so it can be directed or not
	
	/*operations
	* check if element in graph
	* add element to graph
	* find path from A to B
	*/

	private ArrayList<E> nodes;
	private ArrayList<ArrayList<Edge>> neighbors;
	
	public Graph() {
		nodes = new ArrayList<>();
		neighbors = new ArrayList<>();
	}
	
	public Graph(ArrayList<E> nodes, ArrayList<Edge> edges) {
		this.nodes = nodes;
		
		for(Edge e: edges)
			addEdge(e.getX(), e.getY(), e.getWeight());
	}
	
	public int size() {
		return nodes.size();
	}
	
	public ArrayList<E> getNodes() {
		return nodes;
	}
	
	public E getNode(int index) {
		return nodes.get(index);
	}
	
	public int indexOf(E node) {
		return nodes.indexOf(node);
	}
	
	public ArrayList<Integer> getNeighbors(int index) {
		ArrayList<Integer> neighbors = new ArrayList<>();
		
		for(Edge e : this.neighbors.get(index))
			neighbors.add(e.getY());
		
		return neighbors;
	}
	
	public int getDegree(int index) {
		return neighbors.get(index).size();
	}
	
	public double getWeight(int x, int y) throws Exception {
		validateEdge(x, y);
		
		for(Edge e: neighbors.get(x)) {
			if(e.getY() == y)
				return e.getWeight();
		}
		
		throw new Exception("Edge not found");
	}
	
	public void clear() {
		nodes.clear();
		neighbors.clear();
	}
	
	public boolean addNode(E node) {
		if(nodes.contains(node))
			return false;
		
		nodes.add(node);
		return neighbors.add(new ArrayList<Edge>());
	}
	
	public boolean addEdge(int x, int y) {
		return addEdge(new Edge(x, y));
	}
	
	public boolean addEdge(int x, int y, double weight) {
		return addEdge(new Edge(x, y, weight));
	}
	
	public boolean addEdge(Edge e) {
		validateEdge(e.getX(), e.getY());
		
		if(neighbors.get(e.getX()).contains(e))
			return false;
		
		return neighbors.get(e.getX()).add(e);
	}
	
	public boolean removeNode(E node) {
		int index = nodes.indexOf(node);
		
		if(index == -1)
			return false;
		
		for(Edge e: neighbors.get(index))
			neighbors.get(e.getY()).remove(new Edge(e.getY(), e.getX()));
		
		neighbors.remove(index);
		return nodes.remove(node);
	}
	
	public boolean removeEdge(int x, int y) throws Exception {
		validateEdge(x, y);
		
		for(Edge e: neighbors.get(x))
			if(e.getY() == y)
				return neighbors.get(x).remove(e);
		
		throw new Exception("Edge not found");
	}
	
	public void bfs(int x) {
		if(x < 0 || x >= nodes.size())
			throw new IllegalArgumentException("No " + x + " index");
		
		boolean[] isVisited = new boolean[nodes.size()];
		Queue<Integer> queue = new ArrayDeque<>();
		
		queue.add(x);
		isVisited[x] = true;
		
		while(!queue.isEmpty()) {
			int temp = queue.poll();
			System.out.print(nodes.get(temp) + " ");
			
			for(Edge e: neighbors.get(temp)) {
				if(!isVisited[e.getY()]) {
					queue.add(e.getY());
					isVisited[e.getY()] = true;
				}
			}
		}
		
		System.out.println();
	}
	
	public void dfs(int x) {
		if(x < 0 || x >= nodes.size())
			throw new IllegalArgumentException("No " + x + " index");
		
		boolean[] isVisited = new boolean[nodes.size()];		
		dfs(x, isVisited);
		
		System.out.println();
	}
	
	private void dfs(int x, boolean[] isVisited) {
		isVisited[x] = true;
		System.out.print(nodes.get(x) + " ");
		
		for(Edge e: neighbors.get(x)) {
			if(!isVisited[e.getY()])
				dfs(e.getY(), isVisited);
		}
	}

	public double minimumSpanningTree() throws Exception {
		return minimumSpanningTree(0);
	}
	
	public double minimumSpanningTree(int index) throws Exception {
		if(index < 0 || index >= nodes.size())
			throw new IllegalArgumentException("No " + index + " index");
		
		double totalWeight = 0;
		double[] weights= new double[nodes.size()];
		ArrayList<Integer> tree = new ArrayList<>();
		
		for(int i = 0; i < weights.length; i++)
			weights[i] = Double.POSITIVE_INFINITY;
		
		weights[index] = 0;
		
		while(tree.size() < size()) {
			int minIndex= -1;
			double minWeight = Double.POSITIVE_INFINITY;
			
			for(int i = 0; i < size(); i++) {
				if(!tree.contains(i) && weights[i] < minWeight) {
					minWeight = weights[i];
					minIndex = i;
				}
			}
			
			if(minIndex == -1)
				throw new Exception("Graph is not fully connected");

			tree.add(minIndex);
			totalWeight += weights[minIndex];
			System.out.print(nodes.get(minIndex) + " ");
			
			for(Edge e: neighbors.get(minIndex)) {
				if(!tree.contains(e.getY()) && weights[e.getY()] > e.getWeight())
					weights[e.getY()] = e.getWeight();
			}
		}
		
		return totalWeight;
	}
	
	//TODO
	public void shortestPath(int startIndex, int endIndex) {
//		validateEdge(startIndex, endIndex);
//		
//		System.out.print(nodes.get(startIndex) + " ");
//		
//		double[] weights = new double[nodes.size()];
//		Integer[] prev = new Integer[nodes.size()];
//		
//		for(int i = 0; i < nodes.size(); i++) {
//			weights[i] = Double.POSITIVE_INFINITY;
//			prev[i] = null;
//		}
//		
//		weights[startIndex] = 0;
//		
//		
	}
	
	private void validateEdge(int x, int y) {		
		if(x < 0 || x >= nodes.size())
			throw new IllegalArgumentException("No " + x + " index");
		
		if(y < 0 || y >= nodes.size())
			throw new IllegalArgumentException("No " + y + " index");
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(int i = 0; i < neighbors.size(); i++) {
			str.append(getNode(i) + ": ");
			
			for(Edge e : neighbors.get(i))
				str.append("(" + getNode(e.getX()) + ", " + getNode(e.getY()) + ", " + e.getWeight() + ") ");
			
			str.append("\n");
		}
		
		return str.toString();
	}
	
}
