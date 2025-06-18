package org.example.apibudgetbuddyproject.service;

import org.example.apibudgetbuddyproject.exception.ResourceNotFoundException;
import org.example.apibudgetbuddyproject.model.Income;
import org.example.apibudgetbuddyproject.model.Source;
import org.example.apibudgetbuddyproject.model.User;
import org.example.apibudgetbuddyproject.repository.IncomeRepository;
import org.example.apibudgetbuddyproject.repository.SourceRepository;
import org.example.apibudgetbuddyproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// defining IncomeService as a Service class
@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;
    private final SourceRepository sourceRepository;

    // repositories for income, user, and source data access
    @Autowired
    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository, SourceRepository sourceRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
        this.sourceRepository = sourceRepository;
    }

    // service to add an income for a user
    public boolean addIncomeForUser(double amount, int userId, int sourceId) {
        if (amount < 0) {
            return false;
        }
        // Check if source input is null
        Source source = sourceRepository.findById(sourceId).orElseThrow(() -> new ResourceNotFoundException("Source not found with ID: " + sourceId));

        Income income = new Income(amount, userId, sourceId);
        incomeRepository.save(income);
        return true;
    }

    // service to retrieve all the incomes for a user
    public List<Map<String, Object>> getAllIncomesForUser(int userId) {
        List<Income> incomes = incomeRepository.findByUserUserId(userId);

        // returns list of strings and objects (sourceName and amount)
        return incomes.stream().map(income -> {
            Map<String, Object> incomeMap = new HashMap<>();
            incomeMap.put("sourceName", income.getSource().getName());
            incomeMap.put("amount", income.getAmount());
            return incomeMap;
        }).collect(Collectors.toList());
    }
}