package nicemul.ui.frame;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import nicemul.ui.bridge.UIBridge;

public class MainFrame extends Application {

	public Scene scene;

	public WebView webView;

	@Override
	public void start(Stage stage) throws Exception {

		try {
			webView = new WebView();

			JSObject jsobj = (JSObject) webView.getEngine().executeScript("window");
			jsobj.setMember("java", new UIBridge(webView.getEngine()));

			String path = System.getProperty("user.dir");
			path.replace("\\\\", "/");

			scene = new Scene(webView, 800, 600);
			webView.getEngine().load("file:///" + path + "/resources/html/index.html");
			//webView.getEngine().load("file:///" + path + "/resources/demo/demo.html");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WebView getWeb() {
		return webView;
	}

	public void setWeb(WebView web) {
		this.webView = web;
	}
	
	public void start(String[] args) {
		launch(args);
	}

}
