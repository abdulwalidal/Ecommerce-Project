package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String orderBy);

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);

   ProductDTO updateProduct(Product product, Long productId);

    ProductDTO deleteProduct(Long productId);
}
