package implementation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph<E extends Comparable<E>> {
	private boolean isDirected;
	private Map<E, Set<Edge<E>>> list;
	
	public Graph(boolean isDirected) {
		this.isDirected = isDirected;
		list = new HashMap<>();
	}
	
	public Graph(boolean isDirected, E[] nodes, Edge<E>[] edges) {
		this(isDirected);
		
		for(E node: nodes)
			addNode(node);
		
		for(Edge<E> e: edges)
			addEdge(e);
	}
	
	@SuppressWarnings("unchecked")
	public Graph(boolean isDirected, ArrayList<E> nodes, ArrayList<Edge<E>> edges) {
		this(isDirected, (E[]) nodes.toArray(), (Edge<E>[]) edges.toArray());	
	}
	
	public int size() {
		return list.keySet().size();
	}
	
	public boolean isDirected() {
		return isDirected;
	}
	
	public void setIsDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}
	
	public ArrayList<E> getNodes() {
		return new ArrayList<>(list.keySet());
	}
	
	public ArrayList<E> getNeighbors(E node) {
		if(!list.containsKey(node))
			throw new IllegalArgumentException("Node " + node + " does not exist");
		
		ArrayList<E> neighbors = new ArrayList<>();
		
		for(Edge<E> e: list.get(node))
			neighbors.add(e.getY());
		
		return neighbors;
	}
	
	public boolean containsNode(E node) {
		return list.containsKey(node);
	}
	
	public boolean containsEdge(E start, E end) {
		if(!list.containsKey(start) || !list.containsKey(end))
			return false;
		
		return getNeighbors(start).contains(end);
	}
	
	public int getDegree(E node) {
		if(!list.containsKey(node))
			throw new IllegalArgumentException("Node " + node + " does not exist");
		
		return list.get(node).size();
	}
	
	public double getWeight(E start, E end) throws Exception {
		validateEdge(start, end);
		
		for(Edge<E> e: list.get(start))
			if(e.getY().equals(end))
				return e.getWeight();
		
		throw new Exception("Edge not found");
	}
	
	public void clear() {
		list.clear();
	}
	
	public boolean addNode(E node) {
		if(list.containsKey(node))
			return false;
		
		list.put(node, new HashSet<>());
		return true;
	}
	
	public void addNodes(E[] nodes) {
		for(E node: nodes)
			addNode(node);
	}
	
	public boolean addEdge(E start, E end) {
		return addEdge(new Edge<E>(start, end));
	}
	
	public boolean addEdge(E start, E end, double weight) {
		return addEdge(new Edge<E>(start, end, weight));
	}
	
	public boolean addEdge(Edge<E> e) {
		if(!list.containsKey(e.getX()) || !list.containsKey(e.getY()))
			return false;
		
		if(!isDirected)
			list.get(e.getY()).add(new Edge<E>(e.getY(), e.getX(), e.getWeight()));
			
		return list.get(e.getX()).add(e);
	}
	
	public boolean removeNode(E node) {
		if(list.remove(node) == null)
			return false;
				
		for(Set<Edge<E>> edges: list.values())
			edges.removeIf(e -> e.getY().equals(node));
		
		return true;
	}
	
	public boolean removeEdge(E start, E end) throws Exception {
		return removeEdge(new Edge<E>(start, end));
	}
	
	public boolean removeEdge(Edge<E> e) throws Exception {
		if(!isDirected)
			list.get(e.getY()).remove(e);
		
		return list.get(e.getX()).remove(e);
	}
	
	public ArrayList<E> bfs(E node) {		
		if(!list.containsKey(node))
			return null;

		ArrayList<E> traversal = new ArrayList<>();
		HashMap<E, Boolean> isVisited = new HashMap<>();
		Queue<E> queue = new ArrayDeque<>();
		
		queue.add(node);
		isVisited.put(node, true);
		
		while(!queue.isEmpty()) {
			E temp = queue.poll();
			traversal.add(temp);
			for(Edge<E> e: list.get(temp)) {
				E element = e.getY();
				
				if(isVisited.get(element) == null) {
					queue.add(element);
					isVisited.put(element, true);
				}
			}
		}
		
		return traversal;
	}
	
	public ArrayList<E> dfs(E node) {
		if(!list.containsKey(node))
			return null;
		
		ArrayList<E> traversal = new ArrayList<>();
		HashMap<E, Boolean> isVisited = new HashMap<>();
		
		dfs(node, isVisited, traversal);
		return traversal;
	}
	
	private void dfs(E node, HashMap<E, Boolean> isVisited, ArrayList<E> traversal) {
		isVisited.put(node, true);
		traversal.add(node);
		
		for(Edge<E> e: list.get(node))
			if(isVisited.get(e.getY()) == null)
				dfs(e.getY(), isVisited, traversal);
	}

	public ArrayList<E> shortestPath(E start, E end) throws Exception {
		if(!list.containsKey(start) || !list.containsKey(end))
			return null;
		
		ArrayList<E> path = new ArrayList<>();
		HashMap<E, Double> distances = new HashMap<>();
		HashMap<E, E> previous = new HashMap<>();
		HashSet<Edge<E>> edges = new HashSet<>();
		
		for(E node: list.keySet()) {
			distances.put(node, Double.POSITIVE_INFINITY);
			previous.put(node, null);
			edges.addAll(list.get(node));
		}
		
		distances.put(start, 0.0);
		
		for(int i = 0; i < list.keySet().size()-1; i++) {
			for(Edge<E> e: edges) {
				double distance = distances.get(e.getX()) + e.getWeight();
				
				if(distance < distances.get(e.getY())) {
					distances.put(e.getY(), distance);
					previous.put(e.getY(), e.getX());
				}
			}
		}

		//Fail if there's a negative weight cycle
		for(Edge<E> e: edges) {
			if(distances.get(e.getX()) + e.getWeight() < distances.get(e.getY()))
				throw new Exception("Negative-weight cycle exists");
		}
		
		//Fail if there's no possible connection routes between start & end
		if(previous.get(end) == null)
			return null;
		
		for(E node = end; node != null; node = previous.get(node))
			path.add(node);
		
		Collections.reverse(path);
		return path;
	}
	
	private void validateEdge(E start, E end) {		
		if(!list.containsKey(start))
			throw new IllegalArgumentException("No " + start + " node");
		
		if(!list.containsKey(end))
			throw new IllegalArgumentException("No " + end + " node");
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(Map.Entry<E, Set<Edge<E>>> entry: list.entrySet()) {
			str.append(entry.getKey() + ": ");
			
			for(Edge<E> e: entry.getValue())
				str.append("(" + e.getX() + ", " + e.getY() + ", " + e.getWeight() + ") ");
			
			str.append("\n");
		}
		
		return str.toString();
	}
}