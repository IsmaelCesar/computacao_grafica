package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.lang.Math;

import beans.Array;
import beans.Linear;
import beans.Shape;
import beans.Point;
import beans.Triangle;
import beans.ShapeReader;
import beans.PointOperations;

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
	private int selectedObj = 0;
	String selectedShape= objects[this.selectedObj];
	String tempSelected = objects[this.selectedObj];
	//Current Shape
	String currentShape = objects[this.selectedObj];
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
	
	//Illumination and colloring
	@FXML
	TextField txtFieldIamb;
	
	@FXML
	TextField txtFieldIl;
	
	@FXML
	TextField txtFieldOd;
	
	@FXML
	TextField txtFieldPl;
		
	@FXML
	TextField txtFieldKs;
	
	@FXML
	TextField txtFieldKa;
	
	@FXML
	TextField txtFieldEta;
	
	@FXML
	Button btnCalculate;
	
	@FXML
	TextField txtFieldKd;
	
	GraphicsContext gc;
	//Camera Parameters
	//Arrays
	Array N  = new Array(1,3);
	Point pN;
	Array V  = new Array(1,3);
	Point pV;
	Array C  = new Array(1,3);
	Point pC;
	
	//scalars
	double hx;
	double hy;
	double d;
	
	//Illumination and coloring
	Array aIamb = new Array(1,3);
	Point Iamb = new Point(aIamb);
	
	Array aIl = new Array(1,3);
	Point Il = new Point(aIl);
	
	Array aOd = new Array(1,3);
	Point Od  = new Point(aOd);
	
	Array aPl = new Array(1,3);
	Point Pl  = new Point(aPl);
	
	Array aKd = new Array(1,3);
	Point Kd  = new Point(aKd);
	
	double Ks;
	double Ka;
	double Eta;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub		
		initializeMenuButton();		
		gc = canvas.getGraphicsContext2D();			
		this.width = canvas.getWidth();
		this.height= canvas.getHeight();	
		this.zbuffer = new Array(initializeZbufferMatrix((int)this.width,(int)this.height));
		this.gc.setFill(Color.BLACK);
		this.gc.fillRect(0, 0, width, this.height);
		this.initializeCameraParameters();		
		this.drawDefaultShape();
	}
	
	
	public double[][] initializeZbufferMatrix(int width,int height) {
		double zbuffer[][] = new double[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				zbuffer[i][j] = Double.MAX_VALUE;
			}
		}
		return zbuffer;		
	}
	
	private void initializeCameraParameters() { 
		//Camera parameters
		this.txtFieldN.setText("0 1.4 -1");
		this.txtFieldV.setText("0 -1 -1");
		this.txtFieldC.setText("0 -500 500");
		this.txtFieldHX.setText("1.5");
		this.txtFieldHY.setText("1.5");
		this.txtFieldD.setText("7");
		
		this.N  = this.createArrayFromTextFieldValues(this.txtFieldN,this.N);
		this.pN = new Point(N);
		
		this.V  = this.createArrayFromTextFieldValues(this.txtFieldV,this.V);
		this.pV = new Point(V);
		
		this.C  = this.createArrayFromTextFieldValues(this.txtFieldC,this.C);
		this.pC = new Point(C);
		
		this.hx = this.readScalarsFromTextField(this.txtFieldHX);
		this.hy = this.readScalarsFromTextField(this.txtFieldHY);
		this.d = this.readScalarsFromTextField(this.txtFieldD);		
		PointOperations.computePerspective(this.pN, this.pV);
		
		//Illumination and coloring
		this.txtFieldIamb.setText("100 100 100");
		this.txtFieldIl.setText("127 213 254");
		this.txtFieldPl.setText("60 5 -10");
		this.txtFieldKd.setText("0.5 0.3 0.2");
		this.txtFieldOd.setText("0.7 0.5 0.8");
		this.txtFieldKa.setText("0.2");
		this.txtFieldKs.setText("0.5");
		this.txtFieldEta.setText("1");
		
		this.Iamb = new Point(this.createArrayFromTextFieldValues(txtFieldIamb, aIamb));
		this.Il  = new Point(this.createArrayFromTextFieldValues(txtFieldIl, aIl));
		this.Pl  = new Point(this.createArrayFromTextFieldValues(txtFieldPl, aPl));
		this.Kd  = new Point(this.createArrayFromTextFieldValues(txtFieldKd, aKd));
		this.Od  = new Point(this.createArrayFromTextFieldValues(txtFieldKd, aOd));
		this.Ka  = this.readScalarsFromTextField(this.txtFieldKa);
		this.Ks  = this.readScalarsFromTextField(this.txtFieldKs);
		this.Eta  = this.readScalarsFromTextField(this.txtFieldEta);
	}
	
	public void drawDefaultShape() {
		Shape s = sr.read(this.selectedShape);
		PointOperations.computePerspective(this.pN, this.pV);
		s.convertFromWorldToSight(this.pC);
		iterateOverTriangles(s);
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
		zbuffer = new Array(initializeZbufferMatrix((int)width,(int)height));
		Shape s = sr.read(shapeSelected);
		s.convertFromWorldToSight(this.pC);
		iterateOverTriangles(s);
		this.currentShape = shapeSelected;
	}
	
	@FXML
	public void calculateAndDraw(@SuppressWarnings("exports") ActionEvent event) {
		this.gc.setFill(Color.BLACK);
		this.gc.fillRect(0,0,this.width,this.height);
		
		this.N  = this.createArrayFromTextFieldValues(this.txtFieldN,this.N);
		this.pN = new Point(this.N);
		
		this.V  = this.createArrayFromTextFieldValues(this.txtFieldV,this.V);
		this.pV = new Point(this.V);
		
		this.C  = this.createArrayFromTextFieldValues(this.txtFieldC,this.C);
		this.pC = new Point(this.C);
		
		this.hx = this.readScalarsFromTextField(this.txtFieldHX);
		this.hy = this.readScalarsFromTextField(this.txtFieldHY);
		this.d = this.readScalarsFromTextField(this.txtFieldD);		
		PointOperations.computePerspective(this.pN, this.pV);
		
		this.zbuffer = new Array(initializeZbufferMatrix((int)width,(int)height));
		
		ShapeReader sr = new ShapeReader();
		Shape s = sr.read(this.currentShape);
		
		s.convertFromWorldToSight(this.pC);
		iterateOverTriangles(s);
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
	
	/**
	 * Iterates over all vertices in the triangle an sets the norm
	 * of each point with summation of the norms of the triangle 
	 * containing the point 
	 *  
	 * @param t  - list if all triangles of the shape
	 * @param singleT - the triangle currently being verified 
	 */
	public void calculatePointwiseNormal(ArrayList<Triangle> t,Triangle singleT) {
		Array normal = new Array(1,3);
		for(Point p : singleT.getPoints()) {
			for(Triangle mT : t) {
				if(mT.containsPoint(p)) {
					normal = Linear.sum(normal, mT.getNormal());
				}
			}
			p.setNormal(normal.normalization());
		}
		
	}

	//Rasterize
	public void iterateOverTriangles(Shape s) {
		this.gc.setFill(Color.WHITE);
		//ArrayList<Triangle> sortedTs = this.sortTrianglesByBarycenter(s.getTriangles());
		ArrayList<Triangle> sortedTs = s.getTriangles();
		int numTriangles= sortedTs.size();
		for(int i = 0; i<numTriangles ;i++) {	
			Triangle t = sortedTs.get(i);
			calculatePointwiseNormal(sortedTs,t);
			rasterizeTriangle(t);			
		}		
	}
	
	/**
	 * @param triangle - Triangle in sight coordinates
	 */
	public void rasterizeTriangle(Triangle triangle) {
		//Projecting vertices and getting screen coordinates
		ArrayList<Point> pointsScreen = new ArrayList<Point>();
		double screenCoordinates [][][] = new double[3][1][2];
		for(int k =0; k < triangle.getPoints().size(); k++) {
			Point proj  = PointOperations.projectPerspective(triangle.getPoint(k), this.d, this.hx, this.hy);
			screenCoordinates[k][0][0] =  Math.floor(((proj.get(0)+1)/2)*(this.width)+0.5);
			screenCoordinates[k][0][1] = Math.floor(this.height - ((proj.get(1)+1)/2)*(this.height) + 0.5);
			pointsScreen.add(new Point(screenCoordinates[k]));
		}
		
		// RASTERIZE TRIANGLES
		//sort triangles by height
		for(int k =1;k < pointsScreen.size(); k++) {
			int j = k-1;
			Point el = pointsScreen.get(k);
			while(j >=0  && el.get(1) < pointsScreen.get(j).get(1)) {				
				pointsScreen.set(j+1,pointsScreen.get(j));
				j--;
			}			
			pointsScreen.set(j+1, el);
		}
		
		//Gambiarra - DON'T TOUCH IT!
		ArrayList<Point> pBar = new ArrayList<Point>();
		pBar.add(pointsScreen.get(0));
		pBar.add(pointsScreen.get(1));
		pBar.add(pointsScreen.get(2));
		//============================
		
		//calculate division point
		Point division  = calculateTriangleDivisionPoint(pointsScreen);		
		
		if(pointsScreen.get(1).get(0) > division.get(0)) {
			Point swap = pointsScreen.get(1);
			pointsScreen.set(1, division);
			division = swap;
		}
		
		//rasterizing first half of the triangle
		double epsilon = 0.000000000001;
		double a1 = ((pointsScreen.get(1).get(1) - pointsScreen.get(0).get(1))/
				    (pointsScreen.get(1).get(0) - pointsScreen.get(0).get(0)+epsilon));			

		double a2 = ((division.get(1) - pointsScreen.get(0).get(1))/
			    	 (division.get(0) - pointsScreen.get(0).get(0)+epsilon));
		
		
		double  xmin  = pointsScreen.get(0).get(0);
		double  xmax  = pointsScreen.get(0).get(0);
		
		//Creating array objects from points
			
		
		for(int yscan=(int)pointsScreen.get(0).get(1); yscan<= pointsScreen.get(1).get(1);yscan++) {			
			int min = (int)Math.floor(xmin+0.5);
			int max = (int)Math.floor(xmax+0.5);
			for(int j = min; j <= max; j++ ) {
				
				double p[][] = {{(double)j,(double)yscan}};
				Point P = new Point(new Array(p));
				this.zbuffering(P,pBar,triangle,j,yscan);
			}
			xmin += 1/(a1+epsilon);
			xmax += 1/(a2+epsilon);
		}
		
		//rasterizing second half of the triangle
		a1 = ((pointsScreen.get(2).get(1) - pointsScreen.get(1).get(1))/
			  (pointsScreen.get(2).get(0) - pointsScreen.get(1).get(0)+epsilon));
	
		a2 = ((pointsScreen.get(2).get(1) - division.get(1))/
		      (pointsScreen.get(2).get(0) - division.get(0)+epsilon));
		
		xmin = pointsScreen.get(2).get(0);
		xmax = pointsScreen.get(2).get(0);
		for(int yscan=(int)pointsScreen.get(2).get(1); yscan>= (int)pointsScreen.get(1).get(1);yscan--) {			
			int min = (int)Math.floor(xmin+0.5);
			int max = (int)Math.floor(xmax+0.5);
			for(int j = min; j <= max; j++ ) {				
				double p[][] = {{(double)j,(double)yscan}};
				Point P = new Point(new Array(p));
				this.zbuffering(P,pBar,triangle,j,yscan);
			}			
			xmin -= 1/(a1+epsilon);
			xmax -= 1/(a2+epsilon);
		}
	}
	
	/**
	 * @param P - x,y position in screen coordinates
	 * @param tScreen - triangle in screen coordinates
	 * @param tSight  - triangle in sight coordinates
	 * @param i  - x position of the pixel on the screen
	 * @param j  - y position of the pixel on the screen
	 */
	public void zbuffering(Point P,ArrayList<Point> tScreen,Triangle tSight,int i,int j) {
		//The i and j represent the screen coordinates
		//It recieves 3 arrays in order to calculate the barycentric coordinates
		Point baricords = PointOperations.calculateBarycenter(P, tScreen.get(0),tScreen.get(1), tScreen.get(2));
		
		//Getting sight coordinates for p
		double alpha = baricords.get(0);
		double beta  = baricords.get(1);
		double gamma = baricords.get(2);
		
		double x_p = alpha*tSight.getPoint(0).get(0) + beta*tSight.getPoint(1).get(0) + gamma*tSight.getPoint(2).get(0);
		double y_p = alpha*tSight.getPoint(0).get(1) + beta*tSight.getPoint(1).get(1) + gamma*tSight.getPoint(2).get(1);
		double z_p = alpha*tSight.getPoint(0).get(2) + beta*tSight.getPoint(1).get(2) + gamma*tSight.getPoint(2).get(2);
		double pixelPoint_sight[][] = {{x_p,y_p,-z_p}};
		Point PixelPointSight   = new Point(new Array(pixelPoint_sight));
		
		if((i>=0 && j >= 0) && (i< this.width && j < this.height)) {			
			double value = this.zbuffer.getItem(i,j); 
			if(PixelPointSight.get(2) < value) {
				//draw point and save the new value
				this.zbuffer.setItem(PixelPointSight.get(2),i,j);
				this.gc.fillRect(i, j, 1, 1);
				//this.illuminationAndColloring(PixelPointSight, baricords, tSight, i, j);
			}
			
		}
	}
	
	
	/**
	 * @param triangle  - Triangle in sight coordinates
	 * @param baricords - Barycentric coordinates of the point
	 * @return
	 */
	public Array computePointNormVector(Point P,Triangle triangle,Point baricords) {
			double n [][] = new double[1][3];
			n[0][0] = (baricords.get(0)*triangle.getPoint(0).get(0)+
					   baricords.get(1)*triangle.getPoint(1).get(0)+
					   baricords.get(2)*triangle.getPoint(2).get(0));
			
			n[0][1] = (baricords.get(0)*triangle.getPoint(0).get(1)+
					   baricords.get(1)*triangle.getPoint(1).get(1)+
					   baricords.get(2)*triangle.getPoint(2).get(1));
			
			n[0][2] = (baricords.get(0)*triangle.getPoint(0).get(2)+
					   baricords.get(1)*triangle.getPoint(1).get(2)+
					   baricords.get(2)*triangle.getPoint(2).get(2));			
			Array normVector = new Array(n);			
			return normVector.normalization();
	}
	
	public Point computeDifuseComponent(Array normVector,Array dotNL ) {
		Point Id = new Point(new Array(1,3));		
		Point Aux = PointOperations.componentwiseMultiplication(this.Il, this.Od);
		Aux = PointOperations.componentwiseMultiplication(Aux,this.Kd);
		Id = PointOperations.dotScalar(dotNL.getItem(0, 0), Aux);
		return Id;
	}
	
	
	public Point computeSpecularComponent(Array R,Array L,Array vision) {			
		Point Is = new Point(new Array(1,3));						
		Array rvAngle = Linear.dot(R,vision.t());
		if(rvAngle.getItem(0,0) > 0) {
			double base = Math.pow(rvAngle.getItem(0, 0), this.Eta);
			base = base*this.Ks;
			Is = PointOperations.dotScalar(base, this.Il);
		}
		return Is;
	}
	
	/**
	 * @param P - the point corresponding the pixel in screen coordinates
	 * @param triangle - the Triangle in sight coordinates
	 * @param baricords - Barycentric coordinates of P based on the screen coordinates
	 * @param i - x position of the pixel on the screen
	 * @param j - y position of the pixel on the screen
	 */
	public void illuminationAndColloring(Point P,Point baricords,Triangle triangle,int i,int j) {
		//defining origin
		double originCoords[][] = {{0,0,0}};
		Point origin = new Point(new Array(originCoords));
		
		//Computing inportant vectors for Illumination and coloring calculations
		Array normVector = computePointNormVector(P,triangle,baricords);
		P.setNormal(normVector);
		Array visionVector = PointOperations.subtract(origin, P).getArray().normalization();
		Array L = PointOperations.subtract(this.Pl,P).getArray().normalization();
		
		Array aux = Linear.dotScalar(2*Linear.dot(normVector, L.t()).getItem(0, 0),normVector);
		Array R = Linear.subtraction(aux, L);
		
		
		Point Ia = PointOperations.dotScalar(this.Ka, this.Iamb);					
		Array dotNL = Linear.dot(normVector, L.t());
			
		//Defining the difuse component Of light
		Point Id = new Point(new Array(1,3));
		Point Is = new Point(new Array(1,3));
		
		//Array dotNV= Linear.dot(normVector, vision.t());
		
		if(dotNL.getItem(0, 0) < 0) {
			normVector = Linear.dotScalar(-1,normVector);
			dotNL = Linear.dot(normVector,L.t());				
		}					
		
		Id = this.computeDifuseComponent(normVector, dotNL);		
		Is = this.computeSpecularComponent(R, L, visionVector);		
		
		Point Ipoint = PointOperations.sum(PointOperations.sum(Ia, Id),Is);
		//veryfing if any component of the point has a value greater than 255
		//and setting it to 255
		for(int k = 0; k < Ipoint.getArray().getDim();k++) {
			if(Ipoint.get(k) > 255) {
				Ipoint.set(255, k);
			}			
		}		
		Color c = Color.rgb((int)Ipoint.get(0),(int)Ipoint.get(1),(int)Ipoint.get(2));
		this.gc.setFill(c);
		this.gc.fillRect(i,j,1,1);
		
	}
	
	//Utils
	/**
	 * @param d  - Screen Coordinates of the points of the triangle 
	 * @return   coordinates of the division point of the triangle
	 */
	public Point calculateTriangleDivisionPoint(ArrayList<Point> d) {
		double deltaMiddle = d.get(1).get(1) - d.get(0).get(1);
		double deltaBottom = d.get(2).get(1) - d.get(0).get(1);
		double deltaXBottom = d.get(2).get(0) - d.get(0).get(0);
		double xTop = d.get(0).get(0);
		double value[][] = {{xTop + (deltaMiddle/deltaBottom)*deltaXBottom,d.get(1).get(1)}};
		Point p = new Point(new Array(value));
		return p;		
	}
	
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
	
	public ArrayList<Triangle> sortTrianglesByBarycenter(ArrayList<Triangle> t){
		
		for(int i =1;i < t.size(); i++) {
			int j = i-1;
			Triangle el = t.get(i);
			while(j >=0  && el.getBarycenter().get(2) < t.get(j).getBarycenter().get(2)) {
				t.set(j+1, t.get(j));
				j--;
			}
			t.set(j+1, el);
		}
		return t;		
	}
	
}