package com.example.planner.model;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private String description;
    private PriorityLevel priorityLevel;
    private LocalDateTime reminderTime;

    public Task(String title, String description, PriorityLevel priorityLevel, LocalDateTime reminderTime) {
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.reminderTime = reminderTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }
}