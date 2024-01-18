package com.mayaspastries.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mayaspastries.entities.Product;
import com.mayaspastries.repository.ProductRepository;
import com.mayaspastries.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductImpl implements ProductService {

	private ProductRepository repoProduct;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Product> listProduct() {
		// TODO Auto-generated method stub
		return repoProduct.findAll();
	}

	@Override
	public Product addProduct(Product objProduct) {
		// TODO Auto-generated method stub
		return repoProduct.save(objProduct);
	}

	@Override
	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return repoProduct.findById(productId).orElse(null);
	}

	@Override
	public List<Product> findProductsByName(String searchWord) {
		// TODO Auto-generated method stub
		return repoProduct.findAll(searchWord);
	}

	@Override
	public void deleteProduct(int idproduct) {
		// TODO Auto-generated method stub
		repoProduct.deleteById(idproduct);
	}

	@Override
	public Optional<Product> getProductForId(Integer productId) {
		// TODO Auto-generated method stub
		return repoProduct.findById(productId);
	}

	@Override
	public Product updateProduct(Product objProduct) {
		// TODO Auto-generated method stub
		return repoProduct.save(objProduct);
	}

	@Override
	public List<Product> getRandomProducts(int numberOfProducts) {
		List<Integer> allProductIds = getAllProductIds();
		Collections.shuffle(allProductIds);

		List<Product> randomProducts = new ArrayList<>();
		for (int i = 0; i < numberOfProducts && i < allProductIds.size(); i++) {
			int productId = allProductIds.get(i);
			Product product = getProductByIdRamdom(productId);
			if (product != null) {
				randomProducts.add(product);
			}
		}

		return randomProducts;
	}

	private List<Integer> getAllProductIds() {
		// Obtén una lista de todos los IDs de productos disponibles en tu base de
		// datos.
		Query query = entityManager.createQuery("SELECT p.id FROM Product p");
		List<Integer> allProductIds = query.getResultList();
		return allProductIds;
	}

	private Product getProductByIdRamdom(int productId) {
		// Obtén un producto por su ID.
		return entityManager.find(Product.class, productId);
	}
}
