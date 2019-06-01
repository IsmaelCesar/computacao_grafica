package beans;


public class Shape {
	
	private int n_vertex;
	private int n_triangles;
	private int control_verticesW=0;
	private int control_verticesS=0;
	private int control_triangles=0;
	private Array verticesW[];//Vertices in world Coordinates
	private Array verticesS[];//Vertices in sight Coordinates
	private int triangles[][];
	
	public Shape(int vertexes, int triangles) {
		this.n_vertex= vertexes;
		this.n_triangles = triangles;
		this.verticesW = new Array [vertexes];
		this.triangles = new int[triangles][3];
	}
	
	public Array getVerticesW(int i) {
		return this.verticesW[i];
	}
	
	public int [] getTriangleIndexes(int i) {
		return this.triangles[i];
	}
	
	public Array[] getTriangleVerticesW(int i) {
		int indexes []= this.triangles[i];
		Array triangle[] = new Array[indexes.length];
		
		for(int t=0; t < indexes.length;t++) {
			triangle[t] = this.verticesW[indexes[t]];
		}		
		return triangle;		
	}

	public int getN_vertices() {
		return n_vertex;
	}

	public int getN_triangles() {
		return n_triangles;
	}
	
	public void addVertexW(Array A) {
		this.verticesW[this.control_verticesW++] = A;
	}
	
	public void addVertexS(Array A) {
		this.verticesS[this.control_verticesS++] = A;
	}
	
	public void addTriangle(int i []) {
		if(i.length == 3)
			this.triangles[this.control_triangles++] = i;
		else
			throw new IllegalArgumentException("A triangle must have 3 vertices");
	}
	
	public String toString() {
		String retorno = "";
		
		retorno += String.valueOf(this.n_vertex) +" "+String.valueOf(this.n_triangles)+"\n";
		Array a = null;
		for(int v = 0; v < this.n_vertex;v++) {
			a = this.getVerticesW(v);
			retorno += (String.valueOf(a.getItem(0, 0))+" "+
			           String.valueOf(a.getItem(0, 1))+" "+
			           String.valueOf(a.getItem(0, 2))+"\n");
		}
		int indexes [];
		for(int t = 0; t < this.n_triangles;t++) {
			indexes = this.getTriangleIndexes(t);
			retorno += (String.valueOf(indexes[0])+" "+
			           String.valueOf(indexes[1])+" "+
			           String.valueOf(indexes[2])+"\n");
		}
		
		return retorno;
	}

	public Array[] getVerticesW() {
		return verticesW;
	}

	public int[][] getTriangles() {
		return triangles;
	}
	
	
	
}
