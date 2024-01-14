package com.mayaspastries.service;

import java.util.List;
import java.util.Optional;

import com.mayaspastries.entities.Product;

public interface ProductService {

	public List<Product> listProduct();
	
	public Product addProduct(Product objProduct);
	
	public Product getProductById(int productId);
	
	public List<Product> findProductByName(String searchWord);
	
	public Optional<Product> getProductForId(Integer productId);
	
	public Product updateProduct(Product objProduct);
	
	public void deleteProduct(int idproduct);
	
	public List<Product> getRandomProducts(int numberOfProducts);
}
