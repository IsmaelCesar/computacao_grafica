package beans;

import java.util.ArrayList;

public class Shape {
	
	private int n_vertices;
	private int n_triangles;
	private int control_triangles=0;
	private ArrayList<Point> verticesW;//Vertices in world Coordinates	
	private ArrayList<Point> verticesS;//Vertices in sight Coordinates
	private int triangle_indices[][];
	private ArrayList<Triangle> triangles;
	
	public Shape(int vertices, int triangles) {
		this.n_vertices  = vertices;
		this.n_triangles = triangles;
		this.verticesW = new ArrayList<Point>();
		this.verticesS = new ArrayList<Point>();
		this.triangle_indices = new int[triangles][3];
		this.triangles = new ArrayList<Triangle>();
	}
	
	public void addVertex(Array v) {
		this.verticesW.add(new Point(v));		
	}
	
	public void addTriangle(int t[]) {
		Point ps[] = new Point[t.length];
		for(int i = 0; i < t.length; i++) {
			ps[i] = this.verticesW.get(t[i]-1);
		}		
		this.triangles.add(new Triangle(ps[0],ps[1],ps[2]));
		this.triangle_indices[this.control_triangles++]= t;
	}
	
	public Point getVertex(int i) {
		return this.verticesW.get(i);		
	}
	
	public Triangle getTriangle(int i) {
		return this.triangles.get(i);		
	}
	
	public ArrayList<Point> getVerticesSight(){
		return this.verticesS;
	}
	
	public ArrayList<Point> getVerticesWorld(){
		return this.verticesS;
	}
	
	public ArrayList<Triangle> getTriangles(){
		return this.triangles;
	}
	
	/**
	 * Converts all the points of the shape from world coordinates to
	 * sight coordinates. Also replacing all the points in the triangle
	 * from world coordinates to sight coordinates
	 * 
	 * @param C the position of the camera in world coordinates
	 */
	public void convertFromWorldToSight(Point C) {
		
		for(Triangle t : this.triangles) {
			for(int i = 0; i < t.getPoints().size() ; i++) {
				Point persp = PointOperations.applyPerspectiveTransform(t.getPoint(i), C);
				t.setPoint(persp, i);
			}
			t.calculateNormal();
			t.calculateBarycenter();
		}
	}
	
	public void calculateTrianglesBarycenter() {
		for(int i= 0; i < this.n_triangles;i++) {
			this.triangles.get(i).calculateBarycenter();
		}
	}
	
}
