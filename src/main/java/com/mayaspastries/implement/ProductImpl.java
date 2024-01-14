package com.mayaspastries.implement;

import java.util.List;
import java.util.Optional;

import com.mayaspastries.entities.Product;
import com.mayaspastries.service.ProductService;

public class ProductImpl implements ProductService{

	@Override
	public List<Product> listProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product addProduct(Product objProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findProductByName(String searchWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Product> getProductForId(Integer productId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Product updateProduct(Product objProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(int idproduct) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> getRandomProducts(int numberOfProducts) {
		// TODO Auto-generated method stub
		return null;
	}

}
