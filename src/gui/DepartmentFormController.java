package gui;


import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	@FXML
	private DepartmentService service;
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
	public void onBtnSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			 Alerts.showAlert("Success", "Department saved successfully", null, AlertType.INFORMATION);
			 Utils.currentStage(event).close();
		} 
		catch (DbException e) {
			Alerts.showAlert("Error", "Error saving department", e.getMessage(), AlertType.ERROR);
		}
	}
	
	//Método para ler os dados do formulário e criar um objeto Department
	//a partir desses dados. Ele tenta converter o texto do campo de ID 
	//para um inteiro e atribui o nome do departamento ao objeto. 
	//O objeto Department é então retornado.
	private Department getFormData() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
	}
	
	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
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
