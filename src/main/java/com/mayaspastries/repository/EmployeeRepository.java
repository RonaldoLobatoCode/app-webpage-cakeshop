package com.mayaspastries.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayaspastries.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByIduser(int idUser);

}

