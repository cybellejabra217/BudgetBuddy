package org.example.apibudgetbuddyproject.repository;

import org.example.apibudgetbuddyproject.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing expenses.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    // finds user by the userId
    List<Expense> findByUserUserId(int userId);
}