package gui;
import beans.Array;
import beans.Point;
import beans.PointOperations;
import beans.Triangle;
import beans.Linear;
//import beans.Linear.Projections;


public class Questionary {
	
	public static void questao1_letraA() {
		double a[][] ={{1.5,2.5,3.5},
					   {4.5,5.5,6.5}};
		double b[][] ={{7.5,8.5},
				       {9.5,10.5},
				       {11.5,12.5}};
		Array A = new Array(a);
		Array B = new Array(b);
		System.out.println(Linear.dot(A, B));
	}
	
	public static void questao1_letraB() {
		double a[][] ={{3.5,1.5,2.0}};
		double b[][] ={{1.0,2.0,1.5}};
		Array A = new Array(a);
		Array B = new Array(b);
		System.out.println(Linear.subtraction(A, B));
	}
	
	public static void questao1_letraC() {
		double a[][] ={{3.5,1.5,2.0}};
		double b[][] ={{1.0,2.0,1.5}};
		Array A = new Array(a);
		Array B = new Array(b);
		System.out.println(Linear.dot(A, B.t()));
	}
	
	public static void questao1_letraD() {
		double a[][] ={{3.5,1.5,2.0}};
		double b[][] ={{1.0,2.0,1.5}};
		Array A = new Array(a);
		Array B = new Array(b);
		System.out.println(Linear.cross(A, B));
	}
	
	public static void questao1_letraE() {
		double a[][] ={{3.5,1.5,2.0}};
		Array A = new Array(a);
		System.out.println(A.norm());
	}
	
	public static void questao1_letraF() {
		double a[][] ={{3.5,1.5,2.0}};
		Array A = new Array(a);
		System.out.println(A.normalization());
	}	
	
	public static void questao1_letraG() {
		double p_cord [][]= {{1,1}};
		double a [][]= {{1,1}};
		double b [][]= {{1,-1}};
		double c [][]= {{2,1}};
		Array P = new Array(p_cord);
		Array A = new Array(a);
		Array B = new Array(b);
		Array C = new Array(c);
		
		System.out.println(Linear.getBarycentricCoordinates(P, A, B, C));
		
	}
	
	public static void questao1_letraH() {
		double p_cord [][]= {{0.5,0.25,0.25}};
		double a [][]= {{-1,1}};
		double b [][]= {{0,-1}};
		double c [][]= {{1,1}};
		Array Barycentric = new Array(p_cord);
		Array A = new Array(a);
		Array B = new Array(b);		
		Array C = new Array(c);
		System.out.println(Linear.getGeometricFromBarycentric(Barycentric, A, B, C));		
	}
	
	public static void main(String []args) {
		double n[][] = {{-1,-1,-1}};
		Point  N = new Point(n);
		double v[][] = {{0,0,1}};
		Point V = new Point(v);
		double c[][] = {{1,1,2}};
		Point C = new Point(c);
		
		PointOperations.computePerspective(N, V);
		
		double p[][] = {{1,-3,-5}};
		Point P = new Point(p);
		
		P = PointOperations.applyPerspectiveTransform(P, C);
		
		System.out.println(P);
		
	}
}
