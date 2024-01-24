package application;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.print.PrintService;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Controller implements Initializable {

	@FXML
	Button button1;

	@FXML
	Button button2;

	@FXML
	Button button3;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private TextField urlBox;

	private String httpsLink = "http://";
	private String addressLink;

	@FXML
	private WebView webView;

	@FXML
	private ProgressBar progressBar;
	@FXML
	private ComboBox comboBox;

	private WebEngine engine;
	private WebHistory history;

	String homePage;

	private double winZoom;

	// loads the website currently typed into the TextField urlBox
	public void loadSite(ActionEvent actionEvent) {
		addressLink = urlBox.getText().toString();
		if (addressLink.contains("https://")) {
			engine.load(addressLink);
		} else {
			
		engine.load(httpsLink + addressLink);
		}
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
		history = engine.getHistory();
	}

	// reloads the current page
	public void reloadSite() {
		engine.reload();
	}

	// navigates to the previously viewed site
	public void browserBack() {
		history = engine.getHistory();
		history.go(-1);
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		urlBox.setText(entries.get(history.getCurrentIndex()).getUrl());
	}

	// navigates forward through the history, no change if user is on the most
	// recent site
	public void browserForward() {
		history = engine.getHistory();
		history.go(1);
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		urlBox.setText(entries.get(history.getCurrentIndex()).getUrl());
	}

	// zooms in the webview by 10%
	public void zoomWinIn() {
		winZoom = winZoom + .1;
		webView.setZoom(winZoom);
	}

	// zooms out the webview by 10%
	public void zoomWinOut() {
		winZoom = winZoom - .1;
		webView.setZoom(winZoom);
	}

	// loads the site set as the homepage
	public void goHome() {
		engine.load(httpsLink + homePage);
	}

	// sets the current site typed into the text field as the new homepage
	public void setHome() {
		String newHomePage = urlBox.getText().toString();
		homePage = newHomePage;
		engine.load(httpsLink + homePage);
	}

	// outdated method that prints the browser history in the console. not called
	// within the final application
	public void showHistory() {
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		for (WebHistory.Entry entry : entries) {
			System.out.println(entry.getUrl() + " " + entry.getLastVisitedDate());
		}
	}

	// outdated method that displays the history entries within the webview, but not
	// formatted for readability
	public void displayHistory() {
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		engine.loadContent(entries.toString());
	}

	/*
	 * used this tutorial to get printing set up:
	 * https://carlfx.wordpress.com/2013/07/15/introduction-by-example-javafx-8-
	 * printing/
	 */
	public void printSite() {
		Printer printer = Printer.getDefaultPrinter();
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
				Printer.MarginType.DEFAULT);
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null) {
			boolean success = job.printPage(webView);
			if (success) {
				System.out.println("Printing webView!");
				job.endJob();
			}
		}
	}

	// loads the SWEN 501 website in the webview
	public void loadSwen501() {
		addressLink = "ecs.wgtn.ac.nz/Courses/SWEN501_2023T2/WebHome";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// loads the SWEN 502 website in the webview
	public void loadSwen502() {
		addressLink = "ecs.wgtn.ac.nz/Courses/SWEN502_2023T2/WebHome";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// loads the SWEN 504 website in the webview
	public void loadSwen504() {
		addressLink = "ecs.wgtn.ac.nz/Courses/SWEN504_2023T3/";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// loads the program schedule link in the webview
	public void loadProgSched() {
		addressLink = "ecs.wgtn.ac.nz/Courses/SWEN501_2023T2/ProgrammeSchedule";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// should load the java ui link but appears to be blocked on this primative
	// browser type
	public void loadJavaDocumentation() {
		addressLink = "ecs.victoria.ac.nz/foswiki/pub/Main/JavaResources/javaAPI-102.html";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// loads the link to the VUW GitLab
	public void loadGitLink() {
		addressLink = "gitlab.ecs.vuw.ac.nz/";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// opens web-based outlook for VUW email
	public void loadEmailLink() {
		addressLink = "outlook.com/myvuw.ac.nz";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// loads VUW Puaha site
	public void loadPuaha() {
		addressLink = "puaha.wgtn.ac.nz/mydashboard/";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// loads the VUW Nuku
	public void loadNuku() {
		addressLink = "nuku.wgtn.ac.nz/courses/";
		engine.load(httpsLink + addressLink);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

	// allows the user to close the program from a menu button instead of clicking x
	public void closeBrowser() {
		Platform.exit();
	}

	/*
	 * two methods below (getDoc and show NodeContent) were implemented using code
	 * from this resource:
	 * https://stackoverflow.com/questions/26562380/getting-javafx-webengine-
	 * interpreted-document
	 * 
	 * I wanted to load the Document as an html source within the WebView but
	 * couldn't figure out how to make that work. This code did work for printing
	 * the html into the console.
	 */

	public void getDoc() {
		Document doc = engine.getDocument();
		showNodeContent(doc, 0);
	}

	private void showNodeContent(Node n, int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print(" ");
		}
		System.out.println(n.getNodeName() + ":" + n.getNodeValue());
		NodeList children = n.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			showNodeContent(children.item(i), depth + 1);
		}
	}
	/*
	 * Used the following resource to help with changing themes:
	 * https://gist.github.com/pethaniakshay/302072fda98098a24ce382a361bdf477
	 */

	@FXML
	private void handleButtonAction(ActionEvent event) throws Exception {
		Stage primaryStage;
		Parent root;

		if (event.getSource() == button1) {
			primaryStage = (Stage) button1.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("pinkbrowser.fxml"));
		} else if (event.getSource() == button3) {
			primaryStage = (Stage) button1.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("bluebrowser.fxml"));
		}

		else {
			primaryStage = (Stage) button2.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("browser.fxml"));
		}
		Scene scene = new Scene(root, 1680, 1050);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle arg1) {
		engine = webView.getEngine();
		winZoom = 1;
		homePage = "www.google.com";

		/*
		 * this history method was taken from
		 * https://docs.oracle.com/javafx/8/webview/history.htm after I was unable to
		 * figure out how to load the previous history methods in an easily readable
		 * way.
		 */

		final WebHistory history = engine.getHistory();
		history.getEntries().addListener((ListChangeListener.Change<? extends WebHistory.Entry> c) -> {
			c.next();
			c.getRemoved().stream().forEach((e) -> {
				comboBox.getItems().remove(e.getUrl());
			});
			c.getAddedSubList().stream().forEach((e) -> {
				comboBox.getItems().add(e.getUrl());
			});
		});
		comboBox.setOnAction((Event ev) -> {
			int offset = comboBox.getSelectionModel().getSelectedIndex() - history.getCurrentIndex();
			history.go(offset);
		});

		engine.load(httpsLink + homePage);
		progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}

}
