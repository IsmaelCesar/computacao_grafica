package beans;
import java.lang.Math;
public class Array {
	/* This class shall represent all the points, vectors and matrices to be used 
	 * in the implementation 
	 * */
	
	private int dim = 2;
	private int rows_dim = 1;
	private double values[][] = null; 

	public Array(){
		this.values = new double [rows_dim][dim];
	}
	
	public Array(int dim) {
		this.values = new double [rows_dim][dim];
		this.dim = dim;
	}
	
	public Array(int n_rows,int dim){
		this.values = new double [n_rows][dim];
		this.dim = dim;
		this.rows_dim = n_rows;
	}
	
	public Array(double array[][]){
		this.values = array;
		this.dim = array[0].length;
		this.rows_dim = array.length;
	}

	public int getDim() {
		return dim;
	}

	public int getRows_dim() {
		return rows_dim;
	}

	public double[][] getValues() {
		return values;
	}
	
	public Array normalization() {
		Array a = null;
		if(this.rows_dim == 1) {
			double normalized_values[][] = new double[this.rows_dim][this.dim];
			double norm = this.norm();
			for(int i=0; i<this.dim;i++) {
				normalized_values[0][i] = this.values[0][i]/norm;
			}
			a= new Array(normalized_values);
		}
		
		return a;
	}
	
	public Array t(){
		//Transpose the array
		double transposed_array[][] = new double [this.dim][this.rows_dim];
		for(int i = 0;i< this.rows_dim; i++) {
			for(int j = 0;j< this.dim; j++) {
				 transposed_array[j][i]= this.values[i][j];
			}			
		}
		this.values = transposed_array;
		int aux = this.dim;
		this.dim = this.rows_dim;
		this.rows_dim= aux;
		return this;
	}
	
	public double norm() {
		//Returns the norm of a vector
		double r = 0;
		if(this.rows_dim == 1) {
			double sum =0;
			for(int i =0; i< this.dim;i++) {
				sum += this.values[0][i]*this.values[0][i];
			}
			r = Math.sqrt(sum);
		}
		return r;
	}
	
	public String toString() {
		String aString = "[";
		for(int i= 0; i < this.rows_dim;i++) {
			aString += "[";
			for(int j=0;j<this.dim;j++) {
				if(j != this.dim-1)
					aString += String.valueOf(this.values[i][j])+",";
				else
					aString += String.valueOf(this.values[i][j]);
			}
			if(this.rows_dim !=1)
				aString += "]\n";
			else
				aString += "]";
		}
		aString += "]";
		
		return aString;
	}
	
}
