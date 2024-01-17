package com.mayaspastries.service;

import java.util.List;

import com.mayaspastries.entities.Category;

public interface CategoryService {

    public List<Category> listCategory();

    public Category addCategory(Category objCategory);

    public Category getCategoryById(int idcategory);

    public Category updateCategory(Category objCategory);

    public List<Category> findCategoryByName(String searchWord);

    public boolean hasProduct(Integer idcategory);

    public void deleteCategory(int idcategory);
}
