package beans;

public class Point {
	private Array p;
	private Array normal = new Array(1,3);
	
	public Point() {
		this.p = new Array(1,3);
		
	}
	
	public Point(double d[][]){
		if(d.length  == 1) 
			this.p = new Array(d);
		else
			throw new IllegalArgumentException("A point must be a 1xN Double Array");
			
	}
	
	public Point(Array a) {
		if(a.getRows_dim()  == 1) 
			this.p = a;
		else
			throw new IllegalArgumentException("A point must be a 1xN Array");
	}
	
	public double get(int i) {
		return this.p.getItem(0, i);
	}
	
	public void set(double v,int i) {
		this.p.setItem(v, 0, i);
	}
	
	public void setNormal(Array normal) {
		if(normal.getRows_dim()  == 1) 
			this.normal = normal;
		else
			throw new IllegalArgumentException("the normal must be a 1xN Array");
	}
	
	public Array getNormal() {
		return this.normal;
	}
	
	public Array getArray() {
		return this.p;
	}
	
	public String toString() {
		return this.p.toString();
	}
	
	public boolean equals(Point p) {
		boolean result = false;
		if(p.getArray().getDim() == this.p.getDim()) {
			for(int i = 0; i < 3; i++) {
				if(p.get(i) == this.get(i)) 
					result = true;
				else
					result = false;
			}
		}
		return result;
	}

}
