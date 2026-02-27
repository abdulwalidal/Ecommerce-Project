package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private Long nextID = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextID++);
        categories.add(category);

    }
    @Override
    public String deleteCategory(Long categoryId) {
        // Step 1 & 2: Search for the category using a Stream
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        // Step 3: Remove from the list and return result
        categories.remove(category);
        return "Category with categoryId :" + categoryId + "deleted successfully !! ";
    }

}





