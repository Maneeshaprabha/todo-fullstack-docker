package com.backend.test.Entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private boolean isCompleted;

    public Task() {}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.isCompleted = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public boolean isCompleted() { return isCompleted; }
    public void setIsCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }
}
