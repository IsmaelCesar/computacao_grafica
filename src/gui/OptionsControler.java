package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.SplitMenuButton;


public class OptionsControler implements Initializable{
	
	private String objects [] = {"calice2.byu",
			"maca.byu",
			"maca2.byu",
			"vaso.byu",
			"vaca.byu",
			"piramide.byu",
			"triangulo.byu"	};
	public String absolutePath = "/home/ismael/Documentos/Github/computacao_grafica/src/gui/fxml/";
	
	//Variables from FXML
	@FXML
	SplitMenuButton shapeSelectior;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
