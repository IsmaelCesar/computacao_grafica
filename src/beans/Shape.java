package beans;

public class Shape {
	
	private int n_vertices;
	private int n_triangles;
	private int control_vertices =0;
	private int control_triangles=0;
	private Point verticesW[];//Vertices in world Coordinates	
	private Point verticesS[];//Vertices in sight Coordinates
	private int triangle_indices[][];
	private Triangle triangles[];
	
	public Shape(int vertices, int triangles) {
		this.n_vertices  = vertices;
		this.n_triangles = triangles;
		this.verticesW = new Point[vertices];
		this.verticesS = new Point[vertices];
		this.triangle_indices = new int[triangles][3];
		this.triangles = new Triangle [triangles];
	}
	
	public void addVertex(Array v) {
		this.verticesW[this.control_vertices++] = new Point(v);		
	}
	
	public void addTriangle(int t[]) {
		Point ps[] = new Point[t.length];
		for(int i = 0; i < t.length; i++) {
			ps[i] = this.verticesW[i];
		}		
		this.triangles[this.control_triangles] = new Triangle(ps[0],ps[1],ps[2]);
		this.triangle_indices[this.control_triangles++]= t;
	}
	
	public Point getVertex(int i) {
		return this.verticesW[i];		
	}
	
	public Triangle getTriangle(int i) {
		return this.triangles[i];		
	}
	
	
	/**
	 * Converts all the points of the shape from world coordinates to
	 * sight coordinates. Also replacing all the points in the triangle
	 * from world coordinates to sight coordinates
	 * 
	 * @param C the position of the camera in world coordinates
	 */
	public void convertFromWorldToSight(Array C) {
		
		for(int i = 0; i < this.n_vertices;i++) {
			Array a = Linear.Projections.applyPerspectiveTransformation(this.verticesW[i].getArray(), C).t();
			this.verticesS[i] = new Point(a);
		}
		Triangle t = null;
		for(int i =0; i < this.n_triangles;i++) {
			int t_idx[] = this.triangle_indices[0];
			t = this.triangles[i];
			for (int j=0; j < t_idx.length;j++) {
				t.setPoint(verticesS[t_idx[j]], j);
			}
		}
		
	}
	
	
}
