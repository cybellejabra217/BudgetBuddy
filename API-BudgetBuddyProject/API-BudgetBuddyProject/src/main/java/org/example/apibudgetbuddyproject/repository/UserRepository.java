package org.example.apibudgetbuddyproject.repository;

import org.example.apibudgetbuddyproject.model.Category;
import org.example.apibudgetbuddyproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // finds user by the username
    User findByUsername(String username);

    // finds the user by the email
    User findByEmail(String email);
}