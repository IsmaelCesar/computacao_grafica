package beans;

import java.util.ArrayList;

public class Triangle {
	private ArrayList<Point> points = new ArrayList<Point>();
	private Array normal = new Array(1,3);
	private Point barycenter;
	
	public Triangle(Point p1,Point p2,Point p3){
		this.points.add(p1);
		this.points.add(p2);
		this.points.add(p3);
	}

	public void setPoint(Point p,int i) {
		this.points.set(i, p);
	}
	
	public Point getPoint(int i) {
		return this.points.get(i);
	}
	
	public void setNormal(Array normal) {
		if(normal.getRows_dim() == 1)
			this.normal = normal;
		else
			throw new IllegalArgumentException("the normal must be a 1xN Array");
	}
	
	public Array getNormal() {
		return this.normal;
	}
	
	public Point getBarycenter() {
		return this.barycenter;
	}
	
	public ArrayList<Point> getPoints(){
		return this.points;
	}
	
	//Calculate all the coordinates of the barycenter of the triangle
	public Point calculateBarycenter() {
		double d [][] = new double[1][3];
		for(int i = 0; i < d[0].length; i++) {
			for(int j = 0; j < 3;j++ ) {
				d[0][i] += 	this.points.get(j).get(i)/d[0].length;
			}
		}
		this.barycenter = new Point(d);
		return this.barycenter;
	}
	
	public void calculateNormal() {
		Array v1 = Linear.subtraction(this.points.get(1).getArray(), this.points.get(0).getArray());
		Array v2 = Linear.subtraction(this.points.get(2).getArray(), this.points.get(0).getArray());
		Array result = Linear.cross(v1, v2);
		this.setNormal(result.normalization());
	}
	
	public boolean containsPoint(Point p) {
		boolean result = false;
		for(int i = 0; i < this.points.size(); i++) {
			if(this.points.get(i).equals(p)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
}
