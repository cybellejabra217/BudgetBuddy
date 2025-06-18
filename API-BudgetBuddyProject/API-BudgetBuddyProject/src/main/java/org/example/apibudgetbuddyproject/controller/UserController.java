package org.example.apibudgetbuddyproject.controller;

import org.example.apibudgetbuddyproject.model.User;
import org.example.apibudgetbuddyproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// defining controller UserController to handle requests
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // controller for getUserById service
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // controller for addUser service
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        boolean success = userService.addUser(username, password, email);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
    }

    // controller for login service
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String username, @RequestParam String password) {
        boolean loginSuccessful = userService.login(username, password);
        return ResponseEntity.ok(loginSuccessful);
    }
}