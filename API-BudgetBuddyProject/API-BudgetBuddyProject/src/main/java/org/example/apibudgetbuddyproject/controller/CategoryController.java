package org.example.apibudgetbuddyproject.controller;

import org.example.apibudgetbuddyproject.model.Category;
import org.example.apibudgetbuddyproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// defining CategoryController to handle requests
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // controller for getAllCategories service
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // controller for getCategoryIdByName service
    @GetMapping("/{name}")
    public ResponseEntity<Integer> getCategoryIdByName(@PathVariable String name) {
        int categoryId = categoryService.getCategoryIdByName(name);
        if (categoryId != -1) {
            return ResponseEntity.ok(categoryId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // controller for getCategoryNameById service
    @GetMapping("/getCategoryName/{categoryId}")
    public ResponseEntity<String> getCategoryNameById(@PathVariable int categoryId) {
        String categoryName = categoryService.getCategoryNameById(categoryId);
        return ResponseEntity.ok(categoryName);
    }
}