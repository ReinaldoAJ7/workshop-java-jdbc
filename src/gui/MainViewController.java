package gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("Dreams Seller");
	}
	
	// This method is called when the user clicks on the "Department" menu item. 
	//It loads the DepartmentList.fxml view and initializes its controller by setting 
	//the DepartmentService and updating the table view.
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();			
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			URL resource = getClass().getResource(absoluteName);
			if (resource == null) {
				Alerts.showAlert("IO Exception", "Error loading view", "Resource not found: " + absoluteName, AlertType.ERROR);
				return;
			}
			FXMLLoader loader = new FXMLLoader(resource);
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0); //get the first element of the main VBox, which is the menu
			mainVBox.getChildren().clear(); //clear all the content of the main VBox
			mainVBox.getChildren().add(mainMenu); //add the menu back to the main VBox
			mainVBox.getChildren().addAll(newVBox.getChildren()); //add the new content of the new VBox to the main VBox
			
			T controller = loader.getController(); //get the controller of the new view, which is the class that has the @FXML annotations and handles the events of the new view
			initializingAction.accept(controller); //execute the initializing action, which is a lambda expression that receives the controller as a parameter and executes some code to initialize the controller (e.g., set the service, update the table view, etc.)
		}
		catch(Exception e) {
			// Catch broader exceptions (including NullPointer, IllegalState, IOException, etc.) and show a helpful alert
			Alerts.showAlertException("Error loading view", absoluteName, e);
		}
	}
}