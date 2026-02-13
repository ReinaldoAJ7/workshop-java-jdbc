package gui.util;

import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	public static Stage currentStage(Object obj) {
		return (Stage) ((Node) obj).getScene().getWindow();
	}
}
