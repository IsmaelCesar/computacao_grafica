package gui;

import java.net.URL;
import java.util.ResourceBundle;

import beans.Array;
import beans.Shape;
import beans.ShapeReader;
import beans.Linear.Projections;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class OptionsControler implements Initializable{
	
	String objects [] = {"calice2.byu",
			"maca.byu",
			"maca2.byu",
			"vaso.byu",
			"vaca.byu",
			"piramide.byu",
			"triangulo.byu"	};
	String absolutePath = "/home/ismael/Documentos/Github/computacao_grafica/src/gui/fxml/";
	//screen width and height
	int width =512;
	int height =512;
	
	ShapeReader sr = new ShapeReader();
	String selectedShape= objects[0];
	String tempSelected = objects[0];
	
	//Variables from FXML
	@FXML
	MenuButton shapeSelector;
	
	@FXML
	Pane shapeDisplayer;
	
	Canvas canvas;
	
	GraphicsContext gc;
	PixelWriter pw ;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub		
		initializeMenuButton();		
		this.canvas = new Canvas(this.shapeDisplayer.getWidth(),this.shapeDisplayer.getHeight());
		shapeDisplayer.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		pw = gc.getPixelWriter(); 
		drawDefaultShape();
	}
	
	public void drawDefaultShape() {
		Shape s = sr.read(objects[0]);
		compute_coordinates_and_draw_pixels(s,this.pw);
	}
	
	private void initializeMenuButton() {
		int itemsN = 4;
		MenuItem mItems[] = new MenuItem[itemsN];
			
		for(int  i = 0; i < itemsN; i++) {
			mItems[0] = new MenuItem(this.objects[0]);
			mItems[0].setOnAction(event ->{
				selectShapeCallback(((MenuItem)event.getSource()).getText());
				});
		}
		
		this.shapeSelector = new MenuButton("Shapes");
		for(int i =0; i < itemsN;i++)
		   this.shapeSelector.getItems().add(mItems[i]);
		this.shapeSelector.show();
	}
	
	public void selectShapeCallback(String shapeSelected) {
		shapeDisplayer.setStyle("-fx-background-color:black");
		Shape s = this.sr.read(shapeSelected);
		this.compute_coordinates_and_draw_pixels(s, this.pw);
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
	public void compute_coordinates_and_draw_pixels(Shape s,PixelWriter pw) {
		Array shapeVertices [] = applyOrthogonalProjectionToVertexSet(s);
		double x_max = getDimentionMaxValue(shapeVertices,0);
		double x_min = getDimentionMinValue(shapeVertices,0);
		
		double y_max = getDimentionMaxValue(shapeVertices,1);
		double y_min = getDimentionMinValue(shapeVertices,1);
		Array v = null;
		for(int i = 0; i < shapeVertices.length;i++) {
			
			//normalization
			double x = shapeVertices[i].getItem(0,0);
			double y = shapeVertices[i].getItem(0,1);
			
			x = (x-x_min)/(x_max-x_min)*(width-1);
			y = (y-y_min)/(y_max-y_min)*(height-1);
			//System.out.printf("%d,%d\n",x,y);
			pw.setColor((int)x,(int)y,Color.WHITE);
			
		}
		
	}
	
}