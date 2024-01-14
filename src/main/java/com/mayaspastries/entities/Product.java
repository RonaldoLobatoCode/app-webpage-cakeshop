package com.mayaspastries.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idproduct;
	private String name;
	private String description;
	private BigDecimal price;
	private int idcategory;
	private String image;
	private int idemployee;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idcategory", insertable = false, updatable = false)
	private Category objCategory;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idemployee", insertable = false, updatable = false)
	private Employee objEmployee;
}
