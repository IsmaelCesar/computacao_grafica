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
	
	public static Array getBarycentricCoordinates(Array P,Array A,Array B, Array C) {
		Array result = null;
		A = transpose_for_barycentric(A);
		B = transpose_for_barycentric(B);
		C = transpose_for_barycentric(C);
		P = transpose_for_barycentric(P);
		
		if(A.getRows_dim() ==1 && B.getRows_dim() ==1 && C.getRows_dim() ==1 ) {			
			if(A.getDim() == B.getDim()	&& A.getDim() == C.getDim() ) {				
				//creating matrix
				Array T = new Array(A.getDim(),B.getDim());
				double aux [][] = {{P.getItem(0, 0) - C.getItem(0, 0),P.getItem(0, 1) - C.getItem(0, 1)}};
				Array Aux = new Array(aux);
				T.setItem(A.getItem(0, 0) - C.getItem(0,0), 0, 0);
				T.setItem(B.getItem(0, 0) - C.getItem(0,0), 0, 1);				
				T.setItem(A.getItem(0, 1) - C.getItem(0,1), 1, 0);
				T.setItem(B.getItem(0, 1) - C.getItem(0,1), 1, 1);
				
				//creating inverse matrix
				double t_inv[][] = {{T.getItem(1,1),-T.getItem(0,1)},
									{-T.getItem(1, 0),T.getItem(0,0)}};
				
				Array T_inv = new Array(t_inv);
				T_inv = dotScalar(1/determinant(T),T_inv);
				
				Array values = dot(T_inv, Aux.t());
				values = values.t();
				double psi =1 - values.getItem(0, 0) - values.getItem(0,1);
				Array baricords = new Array(1,3);
				baricords.setItem(values.getItem(0, 0), 0, 0);
				baricords.setItem(values.getItem(0, 1), 0, 1);
				baricords.setItem(psi, 0, 2);
				result = baricords;
			}
		}
		
		return result;
		
	}
	
}
