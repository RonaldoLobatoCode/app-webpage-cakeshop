package com.mayaspastries.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mayaspastries.entities.Category;
import com.mayaspastries.entities.Product;
import com.mayaspastries.repository.CategoryRepository;
import com.mayaspastries.repository.ProductRepository;
import com.mayaspastries.service.CategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryImpl implements CategoryService {

    private CategoryRepository repoCategory;

    private ProductRepository repoProduct;

    @Override
    public List<Category> listCategory() {
        // TODO Auto-generated method stub
        return repoCategory.findAll();
    }

    @Override
    public Category addCategory(Category objCategory) {
        // TODO Auto-generated method stub
        return repoCategory.save(objCategory);
    }

    @Override
    public Category getCategoryById(int idcategory) {
        // TODO Auto-generated method stub
        return repoCategory.findById(idcategory).orElse(null);
    }

    @Override
    public Category updateCategory(Category objCategory) {
        // TODO Auto-generated method stub
        return repoCategory.save(objCategory);
    }

    @Override
    public List<Category> findCategoryByName(String searchWord) {
        // TODO Auto-generated method stub
        return repoCategory.findAll(searchWord);
    }

    @Override
    public boolean hasProduct(Integer idcategory) {
        // TODO Auto-generated method stub
        List<Product> products = repoProduct.findByidcategory(idcategory);
        return !products.isEmpty();
    }

    @Override
    public void deleteCategort(int idcategory) {
        // TODO Auto-generated method stub
        repoCategory.deleteById(idcategory);
    }

}
