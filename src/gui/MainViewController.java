package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	private synchronized void loadView(String absoluteName) {
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
		}
		catch(Exception e) {
			// Catch broader exceptions (including NullPointer, IllegalState, IOException, etc.) and show a helpful alert
			Alerts.showAlertException("Error loading view", absoluteName, e);
		}
	}

	//
	private synchronized void loadView2(String absoluteName) {
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
			Node mainMenu = mainVBox.getChildren().get(0);// get the first element of the main VBox, which is the menu
			mainVBox.getChildren().clear();// clear all the content of the main VBox
			mainVBox.getChildren().add(mainMenu);// add the menu back to the main VBox
			mainVBox.getChildren().addAll(newVBox.getChildren());//add the new content of the new VBox to the main VBox
			
			// Controller-specific wiring (defensive checks)
			Object controller = loader.getController();
			if (controller instanceof DepartmentListController) {
				DepartmentListController deptController = (DepartmentListController) controller;
				deptController.setDepartmentService(new DepartmentService());
				deptController.updateTableView();
			} else if (controller == null) {
				// no controller associated with FXML — show warning but do not throw
				Alerts.showAlert("Warning", "No controller", "The loaded view has no controller: " + absoluteName, AlertType.WARNING);
			} else {
				// Controller type mismatch — show alert for easier debugging
				Alerts.showAlert("Warning", "Controller type mismatch", "Expected DepartmentListController for: " + absoluteName, AlertType.WARNING);
			}
		}
		catch(Exception e) {
			Alerts.showAlertException("Error loading view", absoluteName, e);
		}
	}
}