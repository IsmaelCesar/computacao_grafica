module computacao_grafica {
	exports beans;
	exports gui;
	opens gui;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
}