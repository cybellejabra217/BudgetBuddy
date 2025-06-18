package org.example.apibudgetbuddyproject.repository;

import org.example.apibudgetbuddyproject.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing incomes.
 */
@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    // finds user by userId
    List<Income> findByUserUserId(int userId);
}