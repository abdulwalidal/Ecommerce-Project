package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public CategoryResponse findAllCategories(Integer pageNumber, Integer pageSize) {

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
         List<Category> all = categoryPage.getContent(); // will return list of categories
         if (all.isEmpty()) {
            throw new APIException("No category is created till now. ");
        }
     //   ( Converts each Category entity into a CategoryDTO using ModelMapper, and collects the results into a list)
            List<CategoryDTO> categoryDTOS = all.stream()
                    .map(category -> modelMapper.map(category, CategoryDTO.class))
                    .toList();
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setContent(categoryDTOS);
            categoryResponse.setPageNumber(categoryPage.getNumber());
            categoryResponse.setPageSize(categoryPage.getSize());
            categoryResponse.setTotalElements(categoryPage.getTotalElements());
            categoryResponse.setTotalPages(categoryPage.getTotalPages());
            categoryResponse.setLastPage(categoryPage.isLast());
            return categoryResponse;
        }



    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Converts the incoming CategoryDTO (from the request body) into a Category entity.
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB = categoryRepository.findByCategoryName((category.getCategoryName()));
        if(categoryFromDB!=null) {
            throw new APIException("Category with this name " + category.getCategoryName() + " already exists !!!");
        }
        // The new category with name "Clothing" gets an ID (e.g., 3) and is stored.
       Category savedCategory = categoryRepository.save(category);
        //  Converts the saved entity (now with an ID) back to a CategoryDTO and returns it.
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category  = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

       categoryRepository.delete(category);
       //  Converts the deleted entity back to a DTO and returns it.
       return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        //  Finds the existing category by ID; throws ResourceNotFoundException if it doesn’t exist.
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        // Converts the incoming DTO (with updated fields) to a new Category entity.
        // At this point, this entity does not have an ID set
        Category category = modelMapper.map(categoryDTO, Category.class);
      category.setCategoryId(categoryId);
      savedCategory = categoryRepository.save(category);
      return modelMapper.map(savedCategory, CategoryDTO.class);
    }

}




