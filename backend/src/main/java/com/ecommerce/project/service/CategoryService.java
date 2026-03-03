package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;

import java.util.List;


public interface CategoryService {
    List<Category> findAllCategories(Category category);

    void createCategory(Category category);

    String deleteCategory(Long categoryID);

    Category updateCategory(Category category, Long categoryId);




}