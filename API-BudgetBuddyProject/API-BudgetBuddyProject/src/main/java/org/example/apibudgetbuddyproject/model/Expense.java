package org.example.apibudgetbuddyproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

// defining an entity: Expense
@Entity
public class Expense {

    // defining its attributes: expenseID, amount, userID(foreign key), categoryID(foreign key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expenseID", nullable = false)
    private int expenseId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = false)
    private Category category;


    // constructors
    public Expense() {
    }

    public Expense(double amount, int userId, int categoryId) {
        this.amount = amount;
        this.user = new User();
        this.user.setUserId(userId);
        this.category = new Category();
        this.category.setCategoryId(categoryId);
    }

    // Getters and Setters

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    // overrides toString method to print out expense
    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", amount=" + amount +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}