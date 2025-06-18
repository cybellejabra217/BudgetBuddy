package org.example.apibudgetbuddyproject.controller;

import org.example.apibudgetbuddyproject.service.CategoryService;
import org.example.apibudgetbuddyproject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// defining ExpenseController to handle requests
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;


    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Autowired
    private CategoryService categoryService;

    // controller for addExpenseForUser service
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addExpenseForUser(@RequestBody Map<String, Object> requestBody) {
        // Extracting amount from the request body
        Object amountObject = requestBody.get("amount");
        double amount;
        // checks whether to cast amount to int or double depending on input
        if (amountObject instanceof Integer) {
            amount = ((Integer) amountObject).doubleValue();
        } else if (amountObject instanceof Double) {
            amount = (Double) amountObject;
        } else {
            return ResponseEntity.badRequest().build();
        }

        // Extracting userId and categoryId from the request body
        int userId = (int) requestBody.get("userId");
        int categoryId = (int) requestBody.get("categoryId");

        // Call the service layer to add the expense
        boolean result = expenseService.addExpenseForUser(amount, userId, categoryId);
        if (result) {
            // If successful, prepare the response
            String categoryName = categoryService.getCategoryNameById(categoryId);
            Map<String, Object> response = new HashMap<>();
            response.put("amount", amount);
            response.put("categoryName", categoryName);
            return ResponseEntity.ok(response);
        } else {
            // If the expense addition fails, return a response with an error message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to add expense. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // controller for getAllExpensesForUser service
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getAllExpensesForUser(@PathVariable int userId) {
        List<Map<String, Object>> expenses = expenseService.getAllExpensesForUser(userId);
        return ResponseEntity.ok(expenses);
    }
}