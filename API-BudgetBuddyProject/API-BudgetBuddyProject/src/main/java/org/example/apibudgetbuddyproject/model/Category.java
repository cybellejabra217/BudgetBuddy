package org.example.apibudgetbuddyproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

// defining an entity: Category
@Entity
public class Category {

    // defining its attributes: categoryID, categoryName
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryID", nullable = false)
    private int id;

    @NotEmpty(message = "Category name cannot be empty")
    @Column(name = "categoryName", nullable = false)
    private String name;

    // getters and setters
    public int getId() {
        return id;
    }

    public void setCategoryId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // override toString method to print out Category
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}