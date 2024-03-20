package com.example.demo.Category.CategoryService;

import com.example.demo.Category.CategoryController.Request.CategoryRequest;
import com.example.demo.Category.CategoryController.Response.CategoryResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long categoryId) throws ChangeSetPersister.NotFoundException;
    List<CategoryResponse> getAllCategories(Sort.Direction direction, String sortBy);

    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) throws ChangeSetPersister.NotFoundException;

    void deleteCategory(Long categoryId) throws ChangeSetPersister.NotFoundException;

    // Add additional methods as per your requirements
}