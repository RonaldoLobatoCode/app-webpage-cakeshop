package com.mayaspastries.implement;

import org.springframework.stereotype.Service;

import com.mayaspastries.entities.Employee;
import com.mayaspastries.repository.EmployeeRepository;
import com.mayaspastries.service.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeImpl implements EmployeeService{

	private EmployeeRepository repoEmployee;
	
	@Override
	public Integer getEmployeeIdByUsername(int idUser) {
		// TODO Auto-generated method stub
		Employee employee = repoEmployee.findByIduser(idUser);
		return employee != null ? employee.getIdemployee() : null;
	}

}
