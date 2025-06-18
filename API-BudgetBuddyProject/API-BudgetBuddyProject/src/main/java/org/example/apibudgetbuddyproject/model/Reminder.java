package org.example.apibudgetbuddyproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.Date;

// defining entity: Reminder
@Entity
public class Reminder {

    // defining its attributes: reminderID, description, reminderDate, recurrencePattern, userID(foreign key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reminderID", nullable = false)
    private int reminderID;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "reminderDate")
    private Date reminderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrencePattern")
    private RecurrencePattern recurrencePattern;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    // defining the enum values for recurrencePattern
    public enum RecurrencePattern {
        Once,
        Daily,
        Weekly,
        Monthly,
        Yearly
    }

    // constructors
    public Reminder() {

    }
    public Reminder(String description, Date reminderDate, RecurrencePattern recurrencePattern, int userId) {
        this.description = description;
        this.reminderDate = reminderDate;
        this.recurrencePattern = recurrencePattern;
        this.user = new User();
        this.user.setUserId(userId);
    }


    // Getters and Setters

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public RecurrencePattern getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(RecurrencePattern recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    // overrides toString method to print out reminder

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderID=" + reminderID +
                ", description='" + description + '\'' +
                ", reminderDate=" + reminderDate +
                ", recurrencePattern=" + recurrencePattern +
                ", user=" + user +
                '}';
    }
}