package com.example.demo.Category.CategoryService;

import com.example.demo.Category.CategoryController.Request.CategoryRequest;
import com.example.demo.Category.CategoryController.Response.CategoryResponse;
import com.example.demo.Category.CatetoryModel.Category;
import com.example.demo.Category.CategoryRepository.CategoryRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setId(categoryRequest.getId());
        category.setName(categoryRequest.getName());
        category = categoryRepository.save(category);
        return convertToCategoryResponse(category);
    }
    // Utility method to convert Category entity to CategoryResponse
    private CategoryResponse convertToCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }
    @Override
    public CategoryResponse getCategoryById(Long categoryId) throws ChangeSetPersister.NotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        return convertToCategoryResponse(category);
    }
    @Override
    public List<CategoryResponse> getAllCategories(Sort.Direction direction, String sortBy) {
        Sort sort = Sort.by(direction, sortBy);
        List<Category> categories = categoryRepository.findAll(sort);
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for (Category category : categories) {
            CategoryResponse response = convertToCategoryResponse(category);
            categoryResponses.add(response);
        }

        return categoryResponses;
    }
    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) throws ChangeSetPersister.NotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        category.setName(categoryRequest.getName());
        category = categoryRepository.save(category);

        return convertToCategoryResponse(category);
    }

    @Override
    public void deleteCategory(Long categoryId) throws ChangeSetPersister.NotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ChangeSetPersister.NotFoundException();
        }

        categoryRepository.deleteById(categoryId);
    }

}