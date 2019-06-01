package beans;

import beans.Linear.Projections;

//Interface between Linear class and Point class in order to perform Operations
public class PointOperations {
	public static Point subtract(Point p1,Point p2) {
		Point result;		
		Array r = Linear.subtraction(p1.getArray(), p2.getArray());
		result  = new Point(r);		
		return result;
	}
	
	public static Point sum(Point p1,Point p2) {
		Point result;		
		Array r = Linear.sum(p1.getArray(), p2.getArray());
		result  = new Point(r);		
		return result;
	}
	
	public static Point dotScalar(double lambda,Point p2) {
		Point result;		
		Array r = Linear.dotScalar(lambda, p2.getArray());
		result  = new Point(r);		
		return result;
	}
	
	
	//Projections
	public static void computePerspective(Point pN,Point pV) {
		Projections.computePerspectiveMatrix(pN.getArray(),pV.getArray());
	}
	
	public static Point applyPerspectiveTransform(Point p1,Point C) {
		Point result;		
		Array r = Linear.Projections.applyPerspectiveTransformation(p1.getArray(), C.getArray()).t();
		result  = new Point(r);		
		return result;
	}
	
		
}
