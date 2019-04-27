package gui;
import beans.Array;
import beans.Linear;
import beans.Linear.Projections;
import beans.ShapeReader;

public class Questionary {
	
	public static void questao1_letraA() {
		double a[][] ={{1.5,2.5,3.5},
					   {4.5,5.5,6.5}};
		double b[][] ={{7.5,8.5},
				       {9.5,10.5},
				       {11.5,12.5}};
		Array A = new Array(a);
		Array B = new Array(b);
		System.out.println(Linear.dot(A, A));
	}
	
	public static void questao1_letraB() {
		double a[][] ={{3.5,1.5,2.0}};
		double b[][] ={{1.0,2.0,1.5}};
		Array A = new Array(a);
		Array B = new Array(b);
		System.out.println(Linear.subtraction(A, A));
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
		double p_cord [][]= {{-0.25,0.75}};
		double a [][]= {{-1,1}};
		double b [][]= {{0,-1}};
		double c [][]= {{1,1}};
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
		double n [][] = {{1,2,3}};
		double v [][] = {{3,2,1}};
		double u [][] = {{3,1,3}};
		Projections.computePerspectiveMatrix(new Array(n), new Array(u),new Array(v));	
		
	}
}
