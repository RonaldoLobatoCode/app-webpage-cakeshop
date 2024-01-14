package com.mayaspastries.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mayaspastries.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	public List<Product> findAll(String searchWord);
	
	List<Product> findByidcategory(Integer idcategory);
}
