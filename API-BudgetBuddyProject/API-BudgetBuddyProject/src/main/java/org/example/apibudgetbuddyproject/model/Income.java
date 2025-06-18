package org.example.apibudgetbuddyproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

// defining entity: Income
@Entity
public class Income {

    // defining its attributes: incomeID, amount, userID(foreign key), sourceID(foreign key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "incomeID", nullable = false)
    private int incomeId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sourceID", nullable = false)
    private Source source;


    // constructors
    public Income() {

    }
    public Income(double amount, int userId, int sourceId) {
        this.amount = amount;
        this.user = new User();
        this.user.setUserId(userId);
        this.source = new Source();
        this.source.setSourceId(sourceId);
    }

    // Getters and Setters

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }


    // overrides toString method to print out income
    @Override
    public String toString() {
        return "Income{" +
                "incomeId=" + incomeId +
                ", amount=" + amount +
                ", user=" + user +
                ", source=" + source +
                '}';
    }
}