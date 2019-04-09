package gui;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;

public class Screen extends Application{

	private String objects [] = {"vaca.byu",
								"calice2.byu",
								"maca.byu",
								"maca2.byu",
								"piramide.byu",
								"triangulo.byu",
								"vaso.byu"};
	String absolutePath = "/home/ismael/Documentos/Github/computacao_grafica/src/gui/fxml/";
	
	Canvas canvas = new Canvas(512, 512);
	Pane p = new Pane(canvas);
	Scene mScene = new Scene(p);
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		p.setStyle("-fx-background-color:black");
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		PixelWriter pw = gc.getPixelWriter();
		pw.setColor(128,128,Color.WHITE);
		
		stage.setScene(mScene);
		stage.setTitle("Teste");
		stage.show();
	}
	
	public static void main(String []args) {
		launch(args);				
	}

}
