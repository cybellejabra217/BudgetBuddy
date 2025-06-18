package org.example.apibudgetbuddyproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


// defining entity: Source
@Entity
public class Source {

    // defining its attributes: sourceID, sourceName
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "sourceID", nullable = false)
    private int id;

    @NotEmpty(message = "Source name cannot be empty")
    @Column(name = "sourceName", nullable = false)
    private String name;

    // getters and setters

    public int getId() {
        return id;
    }
    public void setSourceId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // overrides toString method to print out Source
    @Override
    public String toString() {
        return "Source{" + "id = " + id + ", name = '" + name + '\'' + "|";
    }
}