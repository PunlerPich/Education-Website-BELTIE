package com.example.demo.Category.CategoryController;

import com.example.demo.Category.CategoryController.Request.CategoryRequest;
import com.example.demo.Category.CategoryController.Response.CategoryResponse;
import com.example.demo.Category.CategoryService.CategoryService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/createCategory")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse createdCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) throws ChangeSetPersister.NotFoundException {
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }
    @GetMapping("/admin/getAllCategory")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        List<CategoryResponse> categories = categoryService.getAllCategories(sortDirection, sortBy);
        return ResponseEntity.ok(categories);
    }
    @PutMapping("/admin/Update/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryRequest categoryRequest) throws ChangeSetPersister.NotFoundException {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/admin/Delete/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) throws ChangeSetPersister.NotFoundException {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}