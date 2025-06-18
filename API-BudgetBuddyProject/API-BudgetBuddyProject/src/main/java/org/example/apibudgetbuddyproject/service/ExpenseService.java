package org.example.apibudgetbuddyproject.service;

import org.example.apibudgetbuddyproject.exception.ResourceNotFoundException;
import org.example.apibudgetbuddyproject.model.Category;
import org.example.apibudgetbuddyproject.model.Expense;
import org.example.apibudgetbuddyproject.model.User;
import org.example.apibudgetbuddyproject.repository.CategoryRepository;
import org.example.apibudgetbuddyproject.repository.ExpenseRepository;
import org.example.apibudgetbuddyproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// defining ExpenseService as a Service class
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    // repositories for expense, user, and category data access
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // service to add an expense for a user
    public boolean addExpenseForUser(double amount, int userId, int categoryId) {
        if (amount < 0) {
            return false;
        }
        // checks if user or category inputted are null
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        Expense expense = new Expense(amount, userId, categoryId);
        expenseRepository.save(expense);
        return true;
    }

    // service to retrieve all expenses for a user from database
    public List<Map<String, Object>> getAllExpensesForUser(int userId) {
        List<Expense> expenses = expenseRepository.findByUserUserId(userId);

        // returns a list of strings and objects (categoryName and amount)
        return expenses.stream().map(expense -> {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("categoryName", expense.getCategory().getName());
            expenseMap.put("amount", expense.getAmount());
            return expenseMap;
        }).collect(Collectors.toList());
    }
}