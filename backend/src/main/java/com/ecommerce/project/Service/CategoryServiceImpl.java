package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
      //  category.setCategoryId(nextID++);
       categoryRepository.save(category);

    }
    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> savedCategoryOptional  = categoryRepository.findById(categoryId);
        Category savedCategory = savedCategoryOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

       categoryRepository.delete(savedCategory);
       return "Your category is deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
      Optional<Category> savedCategoryOptional  = categoryRepository.findById(categoryId);
      Category savedCategory = savedCategoryOptional
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
      category.setCategoryId(categoryId);
      savedCategory = categoryRepository.save(category);
      return savedCategory;
    }

}





