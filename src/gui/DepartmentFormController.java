package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	@FXML
	private Department entity;	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label lblErrorName;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;
	
	@FXML
	public void onBtnSaveAction() {
		System.out.println("onBtnSaveAction");
	}
	@FXML
	public void onBtnCancelAction() {
		System.out.println("onBtnCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;		
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId())); //Convertendo o int para String para exibir no campo de texto
		txtName.setText(entity.getName()); //Exibindo o nome do departamento no campo de texto
	}
	
	
}
