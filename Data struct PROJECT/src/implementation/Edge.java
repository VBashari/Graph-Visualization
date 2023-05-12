package implementation;

public class Edge {
	private double weight;
	private int x, y;
	
	public Edge(int x, int y) {
		this(x, y, 0);
	}
	
	public Edge(int x, int y, double weight) {
		this.x = x;
		this.y = y;
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		return !(o instanceof Edge) ? false : ((Edge)o).getX() == x && ((Edge)o).getY() == y;
	}
}
