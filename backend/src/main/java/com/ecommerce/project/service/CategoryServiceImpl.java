package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse findAllCategories() {
        List<Category> all = categoryRepository.findAll();
        if (all.isEmpty()) {
            throw new APIException("No category is created till now. ");
        }
            List<CategoryDTO> categoryDTOS = all.stream()
                    .map(category -> modelMapper.map(category, CategoryDTO.class))
                    .toList();
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setContent(categoryDTOS);
            return categoryResponse;
        }



    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB = categoryRepository.findByCategoryName((category.getCategoryName()));
        if(categoryFromDB!=null) {
            throw new APIException("Category with this name " + category.getCategoryName() + " already exists !!!");
        }
      //  category.setCategoryId(nextID++);
       Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category  = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

       categoryRepository.delete(category);
       return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Category category = modelMapper.map(categoryDTO, Category.class);
      category.setCategoryId(categoryId);
      savedCategory = categoryRepository.save(category);
      return modelMapper.map(savedCategory, CategoryDTO.class);
    }

}




