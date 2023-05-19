package implementation;

public class Edge<E> {
	private double weight;
	private E x, y;
	
	public Edge(E x, E y) {
		this(x, y, 0);
	}
	
	public Edge(E x, E y, double weight) {
		this.x = x;
		this.y = y;
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public E getX() {
		return x;
	}

	public E getY() {
		return y;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		return !(o instanceof Edge) ? false : ((Edge<E>)o).getX().equals(x) && ((Edge<E>)o).getY().equals(y);
	}
}
