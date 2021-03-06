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
	
	private static Array transpose_for_procedure(Array A) {
		
		if(A.getRows_dim() != 1) {
			return A.t();
		}
		return A;
	}
	
	//Auxiliary method for removing the row i and column j
	protected static Array remove_row_column(Array A,int i,int j) {
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
			det = (a.getItem(0, 0) * a.getItem(1, 1)) - (a.getItem(0, 1) * a.getItem(1, 0));
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
	
//	public static Array getBarycentricCoordinates(Array P,Array A,Array B, Array C) {
//		Array result = null;
//		A = transpose_for_procedure(A);
//		B = transpose_for_procedure(B);
//		C = transpose_for_procedure(C);
//		P = transpose_for_procedure(P);
//		
//		if(A.getRows_dim() ==1 && B.getRows_dim() ==1 && C.getRows_dim() ==1 ) {			
//			if(A.getDim() == B.getDim()	&& A.getDim() == C.getDim() ) {				
//				//creating matrix
//				Array T = new Array(A.getDim(),B.getDim());
//				double aux [][] = {{P.getItem(0, 0) - C.getItem(0, 0),P.getItem(0, 1) - C.getItem(0, 1)}};
//				Array Aux = new Array(aux);
//				T.setItem(A.getItem(0, 0) - C.getItem(0,0), 0, 0);
//				T.setItem(B.getItem(0, 0) - C.getItem(0,0), 0, 1);				
//				T.setItem(A.getItem(0, 1) - C.getItem(0,1), 1, 0);
//				T.setItem(B.getItem(0, 1) - C.getItem(0,1), 1, 1);
//				
//				//creating inverse matrix
//				double t_inv[][] = {{T.getItem(1,1),-T.getItem(0,1)},
//									{-T.getItem(1, 0),T.getItem(0,0)}};
//				
//				double epsilon =0.0000001;
//				Array T_inv = new Array(t_inv);
//				T_inv = dotScalar(1/(determinant(T)+epsilon),T_inv);
//				
//				Array values = dot(T_inv, Aux.t());
//				values = values.t();
//				double psi =1 - values.getItem(0, 0) - values.getItem(0,1);
//				Array baricords = new Array(1,3);
//				baricords.setItem(values.getItem(0, 0), 0, 0);
//				baricords.setItem(values.getItem(0, 1), 0, 1);
//				baricords.setItem(psi, 0, 2);
//				result = baricords;
//			}
//		}
//		return result;		
//	}
	
	//fabio
	public static Array getBarycentricCoordinates(Array P,Array A,Array B, Array C) {
		Array result = null;
		A = transpose_for_procedure(A);
		B = transpose_for_procedure(B);
		C = transpose_for_procedure(C);
		P = transpose_for_procedure(P);		
		if(A.getRows_dim() ==1 && B.getRows_dim() ==1 && C.getRows_dim() ==1 ) {			
			if(A.getDim() == B.getDim()	&& A.getDim() == C.getDim() ) {
				Array v0 = subtraction(B,A);
				Array v1 = subtraction(C,A);
				Array v2 = subtraction(P,A);
			
				double partial = v0.getItem(0, 0)*v1.getItem(0, 1) -v1.getItem(0, 0)*v0.getItem(0, 1);
				double alpha = (v2.getItem(0, 0)*v1.getItem(0, 1) - v1.getItem(0, 0)*v0.getItem(0, 1))/partial;
				double beta = (v0.getItem(0, 0)*v2.getItem(0, 1) - v2.getItem(0, 0)*v0.getItem(0, 1))/partial;
				double gamma = 1.0 - alpha - beta;
				double psi [][] = {{alpha,beta,gamma}};
				result = new Array(psi);				
			}
		}		
		return result;
	}
	
	//lucas
//	public static Array getBarycentricCoordinates(Array P,Array A,Array B, Array C) {
//		Array result = null;
//		A = transpose_for_procedure(A);
//		B = transpose_for_procedure(B);
//		C = transpose_for_procedure(C);
//		P = transpose_for_procedure(P);		
//		if(A.getRows_dim() ==1 && B.getRows_dim() ==1 && C.getRows_dim() ==1 ) {			
//			if(A.getDim() == B.getDim()	&& A.getDim() == C.getDim() ) {
//				Array m1 = new Array(2,2);
//				m1.setItem(A.getItem(0, 0) - C.getItem(0, 0), 0, 0);
//				m1.setItem(B.getItem(0, 0) - C.getItem(0, 0), 0, 1);
//				m1.setItem(A.getItem(0, 1) - C.getItem(0, 1), 1, 0);
//				m1.setItem(B.getItem(0, 1) - C.getItem(0, 1), 1, 1);
//				
//				Array m2 = new Array(2,1);
//				m2.setItem(P.getItem(0,0) - C.getItem(0, 0), 0, 0);
//				m2.setItem(P.getItem(0,1) - C.getItem(0, 1), 1, 0);
//				
//				double d = Linear.determinant(m1);		
//				
//			}
//		}		
//		return result;
//	}
	
	
	public static Array getGeometricFromBarycentric(Array BC,Array A,Array B, Array C) {
		Array result = null;
		A = transpose_for_procedure(A);
		B = transpose_for_procedure(B);
		C = transpose_for_procedure(C);
		BC = transpose_for_procedure(BC);
		if(A.getRows_dim() ==1 && B.getRows_dim() ==1 && C.getRows_dim() ==1 ) {			
			if(A.getDim() == B.getDim()	&& A.getDim() == C.getDim() && BC.getDim() == 3 ) {
				 
				Array P = new Array(1,B.getDim());
				double x0 = (A.getItem(0, 0)*BC.getItem(0, 0) + 
						     B.getItem(0, 0)*BC.getItem(0, 0) + 
						     C.getItem(0, 0)*BC.getItem(0, 0));
				double y0 = (A.getItem(0, 1)*BC.getItem(0, 1) + 
						     B.getItem(0, 1)*BC.getItem(0, 1) + 
						     C.getItem(0, 1)*BC.getItem(0, 1));
				P.setItem(x0, 0,0);
				P.setItem(y0, 0,1);
				result=P;
			}
		}
		return result;
	}
	
	public static Array cross(Array A, Array B) {
		//It only works for tridimentional arrays
		A = transpose_for_procedure(A);
		B = transpose_for_procedure(B);
		Array result = null;
		
		if(A.getRows_dim() == 1 && B.getRows_dim() == 1) {
			if(A.getDim()==3 && B.getDim()==3) {
				result = new Array(1,A.getDim());				
				result.setItem(-(A.getItem(0, 1)*B.getItem(0, 2) - A.getItem(0, 2)*B.getItem(0, 1)),0, 0);
				result.setItem(-(A.getItem(0, 0)*B.getItem(0, 2) - A.getItem(0, 2)*B.getItem(0, 0)),0, 1);
				result.setItem(-(A.getItem(0, 0)*B.getItem(0, 1) - A.getItem(0, 1)*B.getItem(0, 0)),0, 2);				
			}
			else
				throw new IllegalArgumentException("The input arrays dimention do not match.");
		}
		else
			throw new IllegalArgumentException("There is an input array that is not unidimentional.");
		return result;
	}
	
	public static Array[] gramSchmidt(Array N,Array V) {		
		Array result [] = new Array[3];
				
		Array vProjOnToN = Linear.dotScalar(Linear.dot(V,N.t()).getItem(0, 0)/Linear.dot(N, N.t()).getItem(0,0), N);
		Array vO = Linear.subtraction(V, vProjOnToN);	
				
		Array U = Linear.cross(N, vO);

		result[0] = U;
		result[1] = vO;
		result[2] = N;
		
		return result;
	}
	
	public static Array componentwiseMultiplication(Array A,Array B) {
		Array result = new Array(A.getRows_dim(),A.getDim());
		if(A.getRows_dim() == B.getRows_dim()) {
			if(A.getDim() == B.getDim()) {
				
				for(int i =0; i < A.getRows_dim(); i++) {
					for(int j = 0; j < A.getDim(); j++) {
						result.setItem(A.getItem(i, j)*B.getItem(i, j),i,j);
					}
				}
				
			}
			else
				throw new IllegalArgumentException("The dimentions of both arrays must match");			
		}
		else
			throw new IllegalArgumentException("The dimentions of both arrays must match");		
		return result;		
	}
	
	public static Array scalarSubtraction(double lambda,Array A) {
		//labmda - A
		Array result = new Array(A.getRows_dim(),A.getDim());
		
		for(int i = 0; i < A.getRows_dim();i++) {
			for(int j = 0; j< A.getDim();j++) {
				result.setItem(lambda - A.getItem(i, j), i, j);
			}			
		}
		
		return result;
	}
	
	//Implementations of projections
	public static class Projections{
		
		public static Array perspectiveMatrix = new Array(3,3);
		
		public static Array orthogonal(Array A) {
			A = transpose_for_procedure(A);
			Array result = null;
			if(A.getRows_dim() == 1) {
				result = new Array(A.getRows_dim(),A.getDim()-1);
				for(int i = 0; i < result.getDim();i++) {
					result.setItem(A.getItem(0, i), 0, i);
				}
			}
			else
				throw new IllegalArgumentException("The input array must be 1xn");
			return result;
		}
		
		
		//Perspective related transforms and projections
		public static void computePerspectiveMatrix(Array n, Array v) {			
			Array orthogonalized [] = Linear.gramSchmidt(n,v);			
			orthogonalized[0] = orthogonalized[0].normalization();//Norm U
		    orthogonalized[1] = orthogonalized[1].normalization();//Norm VO
			orthogonalized[2] = orthogonalized[2].normalization();//Norm N			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					perspectiveMatrix.setItem(orthogonalized[i].getItem(0, j), i, j);
				}
			}
		}
		
		public static Array applyPerspectiveTransformation(Array P,Array C) {
			Array result = null;
			Array newP = new Array(P.getValues());
			Array newC = new Array(C.getValues());
			if(P.getRows_dim() == 1) {
				newP = P.t(); 
			}
			if(C.getRows_dim() == 1) {
				newC = C.t(); 
			} 
			result = Linear.dot(perspectiveMatrix, Linear.subtraction(newP, newC));
			return result;
		}
		
		public static Array projectPerspective(Array P, double d,double hx,double hy) {
			double def[][]= {{1,0,0},
						     {0,1,0}};
			Array defProj = new Array(def);
			
			Array result = new Array(1,3);
			
			Array p = new Array(P.getValues());			
			if(p.getRows_dim() == 1) {
				result.setItem((d*p.getItem(0, 0))/(p.getItem(0, 2)*hx), 0, 0);
				result.setItem((d*p.getItem(0, 1))/(p.getItem(0, 2)*hy), 0, 1);
				result = Linear.dot(defProj, result.t());
			}
			else
				throw new IllegalArgumentException("The array is not a tridimentional one");
			
			return result;
		}
		
	}
}
