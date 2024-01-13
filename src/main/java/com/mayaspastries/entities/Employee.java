package com.mayaspastries.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idemployee;
    private String name;
    private String lastname;
    private String specialty;
    private String sex;
    private String dni;
    private String email;
    private String phone;
    private String address;
    private int iduser;
}
