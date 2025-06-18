package org.example.apibudgetbuddyproject.controller;

import org.example.apibudgetbuddyproject.service.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// defining CurrentUserController to handle requests
@RestController
@RequestMapping("/api/user")
public class CurrentUserController {

    // controller for getCurrentUserId service
    @GetMapping("/currentUserId")
    public ResponseEntity<Integer> getCurrentUserId() {
        int currentUserId = CurrentUser.getCurrentUserId();
        if (currentUserId != -1) {
            return ResponseEntity.ok(currentUserId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}