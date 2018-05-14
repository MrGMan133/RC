package galekop.be.RC;

import java.io.IOException;

import controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application
{
	private Stage primaryStage;
	private BorderPane rootLayout;
	
    public static void main( String[] args )
    {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Remote Control Manager");
			primaryStage.show();
			MainWindowController controller = loader.getController();
			controller.setApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
