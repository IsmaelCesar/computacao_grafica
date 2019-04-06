package gui;
import beans.Array;
import beans.Linear;

public class Main {
	
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
	public static void main(String []args) {
		questao1_letraG();	
	}
}
