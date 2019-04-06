package beans;

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
}
