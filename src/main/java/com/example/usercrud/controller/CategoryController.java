package com.example.usercrud.controller;

import com.example.usercrud.Response.ApiResponse;
import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Category;
import com.example.usercrud.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody Category category) {
        Category saved = categoryService.addCategory(category);
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "Category created successfully", saved), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "Fetched all categories", categories), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) throws UserNotFound {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "Category fetched successfully", category), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody Category updatedCategory) throws UserNotFound {
        Category category = categoryService.updateCategory(id, updatedCategory);
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "Category updated successfully", category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) throws UserNotFound {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "Category deleted successfully", null), HttpStatus.OK);
    }
}

