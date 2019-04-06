package beans;
import java.lang.Math;


public class Linear {
	public static Array dot(Array a, Array b) {
		Array result = null;
		if(a.getDim() == b.getRows_dim()) {
			double r[][]  = new double [a.getRows_dim()][b.getDim()];
			double value_a [][] = a.getValues();
			double value_b [][] = b.getValues();
			for(int r_a = 0;r_a < a.getRows_dim();r_a++) {				
				for(int k_b = 0; k_b < b.getDim(); k_b++) {
					for(int j = 0; j < a.getDim(); j++) {
						r[r_a][k_b] += value_a[r_a][j]*value_b[j][k_b];
					}
				}	
			}
			
			result = new Array(r);
		}
		else {
			throw new IllegalArgumentException("The columns dimention of first array do not match the "+
											"rows dimention of the second array");
		}
		return result;
	}
	
	public static Array dotScalar(double lambda, Array A) {
		
		Array result= null;
		double v[][] = A.getValues();
		for(int i=0; i<A.getRows_dim();i++) {
			for(int j=0; j< A.getDim();j++) {
				v[i][j] = lambda*v[i][j]; 
			}
		}
		result = new Array(v);
		
		return result;
		
	}
	
	public static Array sum(Array A,Array B) {
		Array result = null;
		
		if(A.getRows_dim() == B.getRows_dim() && A.getDim() == B.getDim()) {
			double r[][] = new double[A.getRows_dim()][A.getDim()];
			double values_A[][] = A.getValues();
			double values_B[][] = B.getValues();
			for(int i =0;i< A.getRows_dim(); i++) {
				for(int j =0;j< A.getDim(); j++) {
					r[i][j] = values_A[i][j] + values_B[i][j];
				}				
			}
			
			result = new Array(r);
		}
		else 
			throw new IllegalArgumentException("Arrays dimentions do not match");
		return result;
	}
	
	public static Array subtraction(Array A,Array B) {
		Array result = null;
		
		if(A.getRows_dim() == B.getRows_dim() && A.getDim() == B.getDim()) {
			double r[][] = new double[A.getRows_dim()][A.getDim()];
			double values_A[][] = A.getValues();
			double values_B[][] = B.getValues();
			for(int i =0;i< A.getRows_dim(); i++) {
				for(int j =0;j< A.getDim(); j++) {
					r[i][j] = values_A[i][j] - values_B[i][j];
				}				
			}
			
			result = new Array(r);
		}
		else 
			throw new IllegalArgumentException("Arrays dimentions do not match");
		return result;
	}
	
	private static Array transpose_for_barycentric(Array A) {
		
		if(A.getRows_dim() != 1) {
			return A.t();
		}
		return A;
	}
	
	//Auxiliary method for removing the row i and column j
	private static Array remove_row_column(Array A,int i,int j) {
		Array newArray = new Array(A.getRows_dim()-1,A.getDim()-1);
		int w = 0, s =0;
		for(int k = 0; k<A.getRows_dim(); k++) {
			s=0;
			for(int l =0; l<A.getDim();l++) {
				if(k!=i && l!=j) {
					newArray.setItem(A.getItem(k, l), w, s);
					s++;					
				}
			}			
			if(k!= i)	
				w++;
		}
		
		return newArray;
		
	}
	
	public static double determinant(Array a) {
		double det = 0;
		if(a.getRows_dim()== 2 && a.getDim() == 2) {
			det = (a.getItem(0, 0) + a.getItem(1, 1)) - (a.getItem(0, 1) + a.getItem(1, 0));
		}
		else {
			Array newA = null;
			for(int j = 0; j < a.getRows_dim();j++) {
				newA = remove_row_column(a,0,j);
				det +=  Math.pow(-1, 0-j)*a.getItem(0, j) * determinant(newA);
			}
		}
			
		return det;
	}
	
	public static Array barycentric_coordinates(Array A,Array B, Array C) {
		Array result = null;
		A = transpose_for_barycentric(A);
		B = transpose_for_barycentric(B);
		C = transpose_for_barycentric(C);
		
		if(A.getRows_dim() ==1 && B.getRows_dim() ==1 && C.getRows_dim() ==1 ) {			
			if(A.getDim() == B.getDim()	&& A.getDim() == C.getDim() ) {				
				//Creating values that sum up to 1
				int dimention = A.getDim();
				Array a = null;
				double r [][] = new double [1][dimention];
				for(int i=0;i< dimention;i++) {
					r[0][i] += 1/((double)dimention);
				}
				
				Array coeficients = new Array(r);				
				
			}
		}
		
		return result;
		
	}
	
}
