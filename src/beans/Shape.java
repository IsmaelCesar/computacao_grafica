package beans;

public class Shape {
	
	private int n_vertex;
	private int n_triangles;
	private Array vertexes[];
	private Array triangles[][];
	
	public Shape(int vertexes, int triangles) {
		this.vertexes = new Array [vertexes];
		this.triangles = new Array[triangles][vertexes];
	}
	
	public Array getVertex(int i) {
		return this.vertexes[i];
	}
	
	public Array [] getTriangle(int i) {
		return this.triangles[i];
	}

	public int getN_vertex() {
		return n_vertex;
	}

	public int getN_triangles() {
		return n_triangles;
	}
	
}
