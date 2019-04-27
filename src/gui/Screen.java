package gui;

import java.net.URL;

import beans.Array;
import beans.Linear;
import beans.Linear.Projections;
import beans.ShapeReader;
import beans.Shape;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Screen extends Application{
	
	String absolutePath = "/home/ismael/Documentos/Github/computacao_grafica/src/gui/";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			  String location = absolutePath+"OptionsControler.fxml";
			  
			  URL url = getClass().getResource("OptionsControler.fxml");
			  Parent root = FXMLLoader.load(url);
			  
		      Scene scene = new Scene(root);
		      primaryStage.setScene(scene);
		      primaryStage.setTitle("Shape Displayer"); 
		      primaryStage.show();
		    } catch (Exception e) {
		      System.out.println("Erro");
		      System.out.println(e.getMessage());
		      e.printStackTrace();
		}
	}
	
	/*public static void main(String []args) {
		launch(args);				
	}*/

}
