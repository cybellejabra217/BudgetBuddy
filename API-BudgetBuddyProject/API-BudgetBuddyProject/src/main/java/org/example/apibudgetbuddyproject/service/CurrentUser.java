package org.example.apibudgetbuddyproject.service;

import org.example.apibudgetbuddyproject.model.User;

// defining class CurrentUser: it saves the user that logs in as CurrentUser for future use
public class CurrentUser {
    private static User currentUser;

    // sets current user
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // clears current user
    public static void clearCurrentUser() {
        currentUser = null;
    }

    // gets the id of the current user
    public static int getCurrentUserId() {
        if (currentUser != null) {
            return currentUser.getUserId();
        } else {
            return -1;
        }
    }
}