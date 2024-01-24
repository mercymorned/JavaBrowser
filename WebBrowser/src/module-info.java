module WebBrowser {
	requires javafx.controls;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.xml;
	requires java.base;
	requires jdk.compiler;
	
	opens application to javafx.graphics, javafx.fxml;
}
