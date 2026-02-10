package gui.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		// Ensure the alert is shown on the JavaFX Application Thread and shown modally
		if (Platform.isFxApplicationThread()) {
			alert.showAndWait();
		} else {
			Platform.runLater(() -> alert.showAndWait());
		}
	}
	
	public static void showAlertException(String title, String header, Exception e) {
		// Print full stacktrace to console for debugging
		e.printStackTrace();
		String content = e.getMessage() == null ? e.toString() : e.getMessage();
		// Keep the alert message short and instruct the user to check the console for details
		showAlert(title, header, content + "\n(see console for stacktrace)", AlertType.ERROR);
	}
}