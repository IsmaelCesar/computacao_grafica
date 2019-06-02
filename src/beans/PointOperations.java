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
	
	public static double dot(Point p1,Point p2) {
		double result = 0;
		if(p1.getArray().getDim() == p2.getArray().getDim()) {
			Array dotPoints  = Linear.dot(p1.getArray(), p2.getArray().t());
			result = dotPoints.getItem(0, 0);			
		}
		else
			throw new IllegalArgumentException("The columns dimentions of each point must match");
		return result;
		
	}
	
	public static Point dotScalar(double lambda,Point p2) {
		Point result;		
		Array r = Linear.dotScalar(lambda, p2.getArray());
		result  = new Point(r);		
		return result;
	}
	
	
	public static Point componentwiseMultiplication(Point p1, Point p2) {
		Array componentMul = Linear.componentwiseMultiplication(p1.getArray(), p2.getArray());
		Point cMul = new Point(componentMul);
		return cMul;
	}
	
	public static Point calculateBarycenter(Point P, Point p1, Point p2, Point p3) {
		Array b = Linear.getBarycentricCoordinates(P.getArray(), p1.getArray(), p2.getArray(), p3.getArray());
		Point baricords = new Point(b);
		return baricords;
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
	
	public static Point projectPerspective(Point p, double d, double hx, double hy) {
		Array result = Linear.Projections.projectPerspective(p.getArray(), d, hx, hy).t();
		Point projectedPoint = new Point(result);
		return projectedPoint;
	}
	
		
}
