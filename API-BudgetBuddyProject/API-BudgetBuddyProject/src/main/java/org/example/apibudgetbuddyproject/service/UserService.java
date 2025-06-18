package org.example.apibudgetbuddyproject.service;


import org.example.apibudgetbuddyproject.exception.ResourceNotFoundException;
import org.example.apibudgetbuddyproject.model.User;
import org.example.apibudgetbuddyproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// defining UserService as a Service class
@Service
public class UserService {

    private final UserRepository userRepository;

    // repository for user data access
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // service to retrieve a user by the userId
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // service to add a user with the credentials
    public boolean addUser(String username, String password, String email) {
        if (userRepository.findByUsername(username) != null) {
            return false; // check if username already exists (can't create a duplicate user)
        }
        // creates new user and sets the username, password and email
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);
        return true;
    }

    // service for the user to login with their credentials
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        // checks if user not NULL and if password matches password in database
        if (user != null && user.getPassword().equals(password)) {
            // sets user as CurrentUser once they log in
            CurrentUser.setCurrentUser(user);
            return true;
        }
        // clears currentUser otherwise
        CurrentUser.clearCurrentUser();
        return false;
    }
}