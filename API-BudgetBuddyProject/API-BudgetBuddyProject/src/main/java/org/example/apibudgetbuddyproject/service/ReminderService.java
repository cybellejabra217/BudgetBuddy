package org.example.apibudgetbuddyproject.service;

import org.example.apibudgetbuddyproject.exception.ResourceNotFoundException;
import org.example.apibudgetbuddyproject.model.Reminder;
import org.example.apibudgetbuddyproject.model.User;
import org.example.apibudgetbuddyproject.repository.ReminderRepository;
import org.example.apibudgetbuddyproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// defining ReminderService as a Service class
@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    // repositories for reminder and user data access
    @Autowired
    public ReminderService(ReminderRepository reminderRepository, UserRepository userRepository) {
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
    }

    // service to retrieve all reminders for a user from the database
    public List<Map<String, Object>> getRemindersByUser(int userId) {
        List<Reminder> reminders = reminderRepository.findByUserUserId(userId);

        // returns a list of strings and objects (desription and date)
        return reminders.stream().map(reminder -> {
            Map<String, Object> reminderMap = new HashMap<>();
            reminderMap.put("description", reminder.getDescription());
            reminderMap.put("date", reminder.getReminderDate());
            return reminderMap;
        }).collect(Collectors.toList());
    }
    // service to add a reminder for a user
    public boolean addReminder(String description, Date reminderDate, String recurrencePatternStr, int userId) {
        if (userId <= 0) {
            return false;
        }
        // checking if user is null
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Reminder.RecurrencePattern recurrencePattern;
        try {
            recurrencePattern = Reminder.RecurrencePattern.valueOf(recurrencePatternStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid recurrence pattern: " + recurrencePatternStr);
        }

        Reminder reminder = new Reminder(description, reminderDate, recurrencePattern, userId);
        reminderRepository.save(reminder);

        return true;
    }

    // service to return the enumerations of recurrence pattern as a list
    public List<String> getRecurrencePatterns() {
        return Arrays.stream(Reminder.RecurrencePattern.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
