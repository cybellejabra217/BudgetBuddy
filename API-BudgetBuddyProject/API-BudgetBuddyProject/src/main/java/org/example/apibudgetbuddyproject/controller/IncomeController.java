package org.example.apibudgetbuddyproject.controller;

import org.example.apibudgetbuddyproject.service.IncomeService;
import org.example.apibudgetbuddyproject.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

// defining controller IncomeController to handle requests
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Autowired
    private SourceService sourceService;

    // controller for addIncomeForUser service
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addIncomeForUser(@RequestBody Map<String, Object> requestBody) {
        // Extracting amount from the request body
        Object amountObject = requestBody.get("amount");
        double amount;
        // checks whether to cast it as int or double depending on input
        if (amountObject instanceof Integer) {
            amount = ((Integer) amountObject).doubleValue();
        } else if (amountObject instanceof Double) {
            amount = (Double) amountObject;
        } else {
            return ResponseEntity.badRequest().build();
        }

        // Extracting userId and sourceId from the request body
        int userId = (int) requestBody.get("userId");
        int sourceId = (int) requestBody.get("sourceId");

        // Call the service layer to add the income
        boolean result = incomeService.addIncomeForUser(amount, userId, sourceId);
        if (result) {
            // If successful, prepare the response
            String sourceName = sourceService.getSourceNameById(sourceId);
            Map<String, Object> response = new HashMap<>();
            response.put("amount", amount);
            response.put("sourceName", sourceName);
            return ResponseEntity.ok(response);
        } else {
            // If the income addition fails, return a response with an error message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to add income. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // controller for getAllExpensesForUser service
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getAllIncomesForUser(@PathVariable int userId) {
        List<Map<String, Object>> incomes = incomeService.getAllIncomesForUser(userId);
        return ResponseEntity.ok(incomes);
    }
}