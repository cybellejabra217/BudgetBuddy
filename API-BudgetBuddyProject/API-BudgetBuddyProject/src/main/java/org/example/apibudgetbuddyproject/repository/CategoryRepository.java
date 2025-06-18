package org.example.apibudgetbuddyproject.repository;

import org.example.apibudgetbuddyproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing categories.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // finds a category by its name
    Optional<Category> findByName(String name);
}