package org.example.apibudgetbuddyproject.service;


import org.example.apibudgetbuddyproject.exception.ResourceNotFoundException;
import org.example.apibudgetbuddyproject.model.Category;
import org.example.apibudgetbuddyproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// defining CategoryService as a Service class
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // the repository for category data access
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    // service to retrieve all categories from database
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // service to get a categoryId by the category's name
    public int getCategoryIdByName(String name) {
        return categoryRepository.findByName(name)
                .map(Category::getId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name));
    }

    // service to get category name by its ID
    public String getCategoryNameById(int categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        // checking if category with such id exists (if yes we return its name)
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category.getName();
        } else {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }
    }
}