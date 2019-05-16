package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.lang.Math;

import beans.Array;
import beans.Linear;
import beans.Shape;
import beans.ShapeReader;
import beans.Linear.Projections;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class OptionsControler implements Initializable{
	
	String objects [] = {"calice2.byu",
			"maca.byu",
			"maca2.byu",
			"vaso.byu",
			"vaca.byu",
			"triangulo.byu",
			"piramide.byu"};
	String absolutePath = "/home/ismael/Documentos/Github/computacao_grafica/src/gui/fxml/";
	//screen width and height
	double width;
	double height;
	
	ShapeReader sr = new ShapeReader();
	String selectedShape= objects[5];
	String tempSelected = objects[5];
	Array zbuffer;
	//Variables from FXML
	@FXML
	MenuButton shapeSelector;
	
	@FXML
	Canvas canvas;
	
	//Text Field
	@FXML
	TextField txtFieldN;
	
	@FXML
	TextField txtFieldV;
	
	@FXML
	TextField txtFieldC;
	
	@FXML
	TextField txtFieldHX;
	
	@FXML
	TextField txtFieldHY;
	
	@FXML
	TextField txtFieldD;
	
	@FXML
	Button btnCalculate;
	
	GraphicsContext gc;
	//Arrays
	Array N  = new Array(1,3);
	Array V  = new Array(1,3);
	Array C  = new Array(1,3);
	//scalars
	double hx;
	double hy;
	double d;
	//Current Shape
	String currentShape = "calice2.byu";
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub		
		initializeMenuButton();		
		gc = canvas.getGraphicsContext2D();			
		this.width = canvas.getWidth();
		this.height= canvas.getHeight();	
		zbuffer = new Array((int)width,(int)height);
		this.gc.setFill(Color.BLACK);
		this.gc.fillRect(0, 0, width, height);
		this.initializeCameraParameters();		
		this.drawDefaultShape();
	}
	
	private void initializeCameraParameters() { 
		this.txtFieldN.setText("0 1.4 -1");
		this.txtFieldV.setText("0 -1 -1");
		this.txtFieldC.setText("0 -500 500");
		this.txtFieldHX.setText("1.5");
		this.txtFieldHY.setText("1.5");
		this.txtFieldD.setText("7");		
		this.N  = this.createArrayFromTextFieldValues(this.txtFieldN,this.N);
		this.V  = this.createArrayFromTextFieldValues(this.txtFieldV,this.V);
		this.C  = this.createArrayFromTextFieldValues(this.txtFieldC,this.C);
		this.hx = this.readScalarsFromTextField(this.txtFieldHX);
		this.hy = this.readScalarsFromTextField(this.txtFieldHY);
		this.d = this.readScalarsFromTextField(this.txtFieldD);		
		Projections.computePerspectiveMatrix(this.N, this.V);	
	}
	
	public void drawDefaultShape() {
		Shape s = sr.read(objects[5]);
		iterateOverTriangles(s,gc);
	}
	
	private void initializeMenuButton() {
		int itemsN = 6;
		ArrayList<MenuItem> mItems = new ArrayList<MenuItem>();
			
		for(int  i = 0; i < itemsN; i++) {
			mItems.add(new MenuItem(this.objects[i]));
			mItems.get(i).setOnAction(event ->{
				selectShapeCallback(((MenuItem)event.getSource()).getText());
				});
		}
		
		this.shapeSelector.getItems().addAll(mItems);
		this.shapeSelector.show();
	}
	
	public void selectShapeCallback(String shapeSelected) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0,0, this.width,this.height);
		zbuffer = new Array((int)width,(int)height);
		Shape s = sr.read(shapeSelected);
		iterateOverTriangles(s,gc);
		this.currentShape = shapeSelected;
	}
	
	@FXML
	public void calculateAndDraw(@SuppressWarnings("exports") ActionEvent event) {
		this.gc.setFill(Color.BLACK);
		this.gc.fillRect(0,0,this.width,this.height);
		this.N  = this.createArrayFromTextFieldValues(this.txtFieldN,this.N);
		this.V  = this.createArrayFromTextFieldValues(this.txtFieldV,this.V);
		this.C  = this.createArrayFromTextFieldValues(this.txtFieldC,this.C);
		this.hx = this.readScalarsFromTextField(this.txtFieldHX);
		this.hy = this.readScalarsFromTextField(this.txtFieldHY);
		this.d = this.readScalarsFromTextField(this.txtFieldD);		
		Projections.computePerspectiveMatrix(this.N, this.V);
		zbuffer = new Array((int)width,(int)height);
		ShapeReader sr = new ShapeReader();
		Shape s = sr.read(this.currentShape);
		iterateOverTriangles(s,gc);
	}
	
	
	//Internal functions
	public static double getDimentionMaxValue(Array vertexes[],int dim) {
		double value= 0;
			
		for(int v = 0; v < vertexes.length;v++) {
			if(vertexes[v].getItem(0,dim) > value)
				value = vertexes[v].getItem(0,dim);
		}		
		return value;		
	}
	
	public static double getDimentionMinValue(Array vertexes[],int dim) {
		double value= 0;
		value = vertexes[0].getItem(0,dim);
		
		for(int v = 0; v < vertexes.length;v++) {
			if(vertexes[v].getItem(0,dim) < value)
				value = vertexes[v].getItem(0,dim);
		}		
		return value;		
	}
	
	public static Array[] applyOrthogonalProjectionToVertexSet(Shape s) {
		Array values [] = new Array[s.getVertexes().length];		
		for(int i = 0; i<s.getVertexes().length;i++) {
			values[i] = Projections.orthogonal(s.getVertex(i));
		}		
		return values;	
	}
	
	@SuppressWarnings("exports")
	public void compute_coordinates_and_draw_pixels_orthogonal(Shape s,GraphicsContext gc) {
		Array shapeVertices [] = applyOrthogonalProjectionToVertexSet(s);
		double x_max = getDimentionMaxValue(shapeVertices,0);
		double x_min = getDimentionMinValue(shapeVertices,0);
		
		double y_max = getDimentionMaxValue(shapeVertices,1);
		double y_min = getDimentionMinValue(shapeVertices,1);
		for(int i = 0; i < shapeVertices.length;i++) {
			
			//normalization
			double x = shapeVertices[i].getItem(0,0);
			double y = shapeVertices[i].getItem(0,1);
			
			x = (x-x_min)/(x_max-x_min)*(width-1);
			y = (y-y_min)/(y_max-y_min)*(height-1);
			gc.setFill(Color.WHITE);
			gc.fillRect(x,y, 1,1);			
		}		
	}
	
	public void compute_coordinates_and_draw_pixels_perspective(Shape s,@SuppressWarnings("exports") GraphicsContext gc) {
		Array verticesSet [] = s.getVertexes();
		for(int i = 0; i < verticesSet.length;i++) {
			//projecting vertex
			Array aux = verticesSet[i];
			aux = Projections.applyPerspectiveTransformation(aux, this.C).t();
			aux = Projections.projectPerspective(aux,this.d,this.hx,this.hy).t();
			double k = Math.floor(((aux.getItem(0, 0)+1)/2)*(this.width)+0.5);
			double l = Math.floor(this.height - ((aux.getItem(0, 1)+1)/2)*(this.height) + 0.5);
			gc.setFill(Color.WHITE);
			gc.fillRect(k,l,1,1);
		}		
	}
	
	//Rasterize
	public void iterateOverTriangles(Shape s, @SuppressWarnings("exports") GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		int numTriangles= s.getTriangles().length;
		for(int i = 0; i<numTriangles ;i++) {
	        int triangleIndices []= s.getTriangleIndexes(i);
			//Get all vertices
			Array vertices [] = new Array [3];
			for(int k = 0;k < vertices.length;k++) {
				vertices[k] = s.getVertex(triangleIndices[k]-1); 
			}
			rasterizeTriangle(vertices,gc);			
		}		
	}
	
	public void rasterizeTriangle(Array triangle[],@SuppressWarnings("exports") GraphicsContext gc) {
		//Projecting vertices and getting screen coordinates
		Array aux = null;
		double sightCoordinates [][] = new double [3][2];
		double screenCoordinates [][] = new double [3][2];
		for(int k =0;k < triangle.length; k++) {
			aux = Projections.applyPerspectiveTransformation(triangle[k], this.C).t();
			aux = Projections.projectPerspective(aux, this.d, this.hx, this.hx).t();
			sightCoordinates[k][0] = aux.getItem(0, 0);
			sightCoordinates[k][1] = aux.getItem(0, 1);
			screenCoordinates[k][0] = Math.floor(((aux.getItem(0, 0)+1)/2)*(this.width)+0.5);
			screenCoordinates[k][1] = Math.floor(this.height - ((aux.getItem(0, 1)+1)/2)*(this.height) + 0.5);
			
		}
		// RASTERIZE TRIANGLES
		//sort triangles by height
		for(int k =1;k < screenCoordinates.length; k++) {
			int j = k-1;
			double el[] = screenCoordinates[k];
			while(j >=0  && el[1] < screenCoordinates[j][1]) {				
				screenCoordinates[j+1] = screenCoordinates[j];
				j--;
			}
			screenCoordinates[j+1] = el;
		}
		
		//calculate division point
		double deltaMiddle = screenCoordinates[1][1] - screenCoordinates[0][1];
		double deltaBottom = screenCoordinates[2][1] - screenCoordinates[0][1];
		double deltaXBottom = screenCoordinates[2][0] - screenCoordinates[0][0];
		double xTop = screenCoordinates[0][0];
		double division [] = {xTop + (deltaMiddle/deltaBottom)*deltaXBottom,screenCoordinates[1][1]};
		
		if(screenCoordinates[1][0] > division[0]) {
			double swap[] = screenCoordinates[1];
			screenCoordinates[1] = division;
			division = swap;
		}
		
		//rasterizing first half of the triangle
		double epsilon = 0.000000000001;
		double a1 = ((screenCoordinates[1][1] - screenCoordinates[0][1])/
				    (screenCoordinates[1][0] - screenCoordinates[0][0]+epsilon));			

		double a2 = ((division[1] - screenCoordinates[0][1])/
			    	 (division[0] - screenCoordinates[0][0]+epsilon));	
		
		double  xmin  = screenCoordinates[0][0];
		double  xmax  = screenCoordinates[0][0];
		
		for(int yscan=(int)screenCoordinates[0][1]; yscan<= screenCoordinates[1][1];yscan++) {			
			int min = (int)Math.floor(xmin+0.5);
			int max = (int)Math.floor(xmax+0.5);
			for(int j = min; j <= max; j++ ) {
				
				//Creating array objects from points
				double a[][] = {{screenCoordinates[0][0],screenCoordinates[0][1]}};
				double b[][] = {{Math.floor(xmin+0.5),(double)yscan}};
				double c[][] = {{Math.floor(xmax+0.5),(double)yscan}};
				double p[][] = {{(double)j,(double)yscan}};
				Array A = new Array(a);
				Array B = new Array(b);
				Array C = new Array(c);
				Array P = new Array(p);
				
				if(j> min && j < max) {
					this.zbuffering(P,A,B,C,j,yscan);
				}
				else if(j== min) {					
					this.zbuffering(B,A,B,C,j,yscan);
				}
				else if(j==max) {
					this.zbuffering(C,A,B,C,j,yscan);
				}
				//gc.fillRect(j, yscan, 1, 1);
			}
			xmin += 1/(a1+epsilon);
			xmax += 1/(a2+epsilon);
		}
		
		//rasterizing second half of the triangle
		a1 = ((screenCoordinates[2][1] - screenCoordinates[1][1])/
			  (screenCoordinates[2][0] - screenCoordinates[1][0]+epsilon));
	
		a2 = ((screenCoordinates[2][1] - division[1])/
		      (screenCoordinates[2][0] - division[0]+epsilon));
		
		xmin = screenCoordinates[2][0];
		xmax = screenCoordinates[2][0];
		for(int yscan=(int)screenCoordinates[2][1]; yscan>= (int)screenCoordinates[1][1];yscan--) {			
			int min = (int)Math.floor(xmin+0.5);
			int max = (int)Math.floor(xmax+0.5);
			for(int j = min; j <= max; j++ ) {
				//Creating array objects from points
				double a[][] = {{screenCoordinates[2][0],screenCoordinates[2][1]}};
				double b[][] = {{Math.floor(xmin+0.5),(double)yscan}};
				double c[][] = {{Math.floor(xmax+0.5),(double)yscan}};
				double p[][] = {{(double)j,(double)yscan}};
				Array A = new Array(a);
				Array B = new Array(b);
				Array C = new Array(c);
				Array P = new Array(p);
				
				if(j> min && j < max) {
					this.zbuffering(P,A,B,C,j,yscan);
				}
				else if(j== min) {					
					this.zbuffering(B,A,B,C,j,yscan);
				}
				else if(j==max) {
					this.zbuffering(C,A,B,C,j,yscan);
				}
				//gc.fillRect(j, yscan, 1, 1);
			}			
			xmin -= 1/(a1+epsilon);
			xmax -= 1/(a2+epsilon);
		}
	}
	
	
	public void zbuffering(Array P,Array A, Array B, Array C,int i,int j) {
		//The i and j represent the screen coordinates
		//It recieves 3 arrays in order to calculat the baricentric coordinates
		Array baricords = Linear.getBarycentricCoordinates(P, A, B, C);
		if((i>=0 && j >= 0) && (i< this.width && j < this.height)) {
			double value = this.zbuffer.getItem(i,j); 
			if(value < baricords.getItem(0,2)) {
				//draw point and save the new value
				this.zbuffer.setItem(baricords.getItem(0,2),i,j);
				this.gc.fillRect(i,j,1,1);
			}
		}
	}
	
	//Utils	
	public Array createArrayFromTextFieldValues(@SuppressWarnings("exports") TextField tField,Array A) {
		String values = tField.getText() + " ";
		String number ="";
		int k = 0;
		for(int i = 0; i < values.length();i++) {			
			if(values.charAt(i)  != ' ') {
				number += values.charAt(i);
			}
			else {
				if(k < A.getDim())
					A.setItem(Double.valueOf(number), 0, k++);
				else
					break;
				number = "";
			}				
		}		
		return A;
	}
	
	public double readScalarsFromTextField(@SuppressWarnings("exports") TextField tField) {
		double scalar = 0;
		String values = tField.getText() + " ";
		String number ="";
		int k = 0;
		for(int i = 0; i < values.length();i++) {			
			if(values.charAt(i)  != ' ') {
				number += values.charAt(i);
			}
			else {
				if(k < 1) {
					scalar = Double.valueOf(number);
					k++;
				}	
				else
					break;
				number = "";
			}				
		}
		return scalar;	
	}
	
}