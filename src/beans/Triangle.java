package beans;

import java.util.ArrayList;

public class Triangle {
	private ArrayList<Point> points = new ArrayList<Point>();
	private Array normal = new Array(1,3);
	
	Triangle(){
	}
	
	Triangle(Point p1,Point p2,Point p3){
		this.points.add(p1);
		this.points.add(p2);
		this.points.add(p3);
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
	
}
