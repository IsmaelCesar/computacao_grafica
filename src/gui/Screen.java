package gui;

import beans.Array;
import beans.Linear;
import beans.Linear.Projections;
import beans.ShapeReader;
import beans.Shape;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Screen extends Application{

	private String objects [] = {"calice2.byu",
								"maca.byu",
								"maca2.byu",
								"vaso.byu",
								"vaca.byu",
								"piramide.byu",
								"triangulo.byu"	};
	String absolutePath = "/home/ismael/Documentos/Github/computacao_grafica/src/gui/fxml/";
	
	int width =512;
	int height =512;
	Canvas canvas = new Canvas(width, height);
	Pane p = new Pane(canvas);
	Scene mScene = new Scene(p);
	
	@SuppressWarnings("exports")
	public void compute_coordinates_and_draw_pixels(Shape s,PixelWriter pw) {
		double x_max = s.getDimentionMaxValue(0);
		double x_min = s.getDimentionMinValue(0);
		
		double y_max = s.getDimentionMaxValue(1);
		double y_min = s.getDimentionMinValue(1);
		Array v = null;
		for(int i = 0; i < s.getN_vertex();i++) {
			v = s.getVertex(i);
			v = Projections.orthogonal(v);
			//normalization
			double x = v.getItem(0, 0);
			double y = v.getItem(0, 1);
			
			x = (x-x_min)/(x_max-x_min)*(width-1);
			y = (y-y_min)/(y_max-y_min)*(height-1);
			//System.out.printf("%d,%d\n",x,y);
			pw.setColor((int)x,(int)y,Color.WHITE);
			
		}
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		p.setStyle("-fx-background-color:black");
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		PixelWriter pw = gc.getPixelWriter();
		
		
		ShapeReader sr = new ShapeReader();
		Shape s = sr.read(objects[0]);
		compute_coordinates_and_draw_pixels(s,pw);
		
		stage.setScene(mScene);
		stage.setTitle("Teste");
		stage.show();
	}
	
	public static void main(String []args) {
		launch(args);				
	}

}
