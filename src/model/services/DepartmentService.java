package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	public List<Department> findAll(){
		
		return dao.findAll();
	}
	
	//Inserir um novo departamento ou atualizar 
	//um departamento existente dependendo se o 
	//id do departamento é nulo ou não 
	public void saveOrUpdate(Department obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
}
