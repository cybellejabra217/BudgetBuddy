package org.example.apibudgetbuddyproject.repository;

import org.example.apibudgetbuddyproject.model.Category;
import org.example.apibudgetbuddyproject.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Repository interface for managing reminders.
 */
@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

    // finds user by userId
    List<Reminder> findByUserUserId(int userId);

    List<Reminder> findByReminderDate(Date date);
}