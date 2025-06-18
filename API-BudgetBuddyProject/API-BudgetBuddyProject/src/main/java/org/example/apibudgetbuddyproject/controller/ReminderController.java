package org.example.apibudgetbuddyproject.controller;

import org.example.apibudgetbuddyproject.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// defining controller ReminderController to handle requests
@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    // controller for getRemindersByUser service
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getAllRemindersForUser(@PathVariable int userId) {
        List<Map<String, Object>> reminders = reminderService.getRemindersByUser(userId);
        return ResponseEntity.ok(reminders);
    }

    // controller for addReminder service
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addReminder(@RequestBody Map<String, Object> requestBody) {
        System.out.println(requestBody);
        String description = (String) requestBody.get("description");
        String reminderDateStr = (String) requestBody.get("reminderDate");
        Date reminderDate = Date.valueOf(reminderDateStr);
        String recurrencePatternStr = (String) requestBody.get("recurrencePatternStr");
        int userId = (int) requestBody.get("userId");

        boolean result = reminderService.addReminder(description, reminderDate, recurrencePatternStr, userId);
        if (result) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("description", description);
            response.put("reminderDate", reminderDate);
            response.put("recurrencePatternStr", recurrencePatternStr);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // controller for getRecurrencePatterns service
    @GetMapping("/getRecurrencePatterns")
    public ResponseEntity<List<String>> getRecurrencePatterns() {
        List<String> recurrencePatterns = reminderService.getRecurrencePatterns();
        return ResponseEntity.ok(recurrencePatterns);
    }
}
