package com.example.usercrud.service;

import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Category;
import com.example.usercrud.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) throws UserNotFound {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("Category not found with ID: " + id));
    }

    public Category updateCategory(Long id, Category updatedCategory) throws UserNotFound {
        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        category.setType(updatedCategory.getType());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) throws UserNotFound {
        if (!categoryRepository.existsById(id)) {
            throw new UserNotFound("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}

