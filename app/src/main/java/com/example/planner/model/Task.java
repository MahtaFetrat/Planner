package com.example.planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private PriorityLevel priorityLevel;

    @ColumnInfo
    private String reminderTime;

    public Task(String title, String description, PriorityLevel priorityLevel, String reminderTime) {
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.reminderTime = reminderTime;
    }

    public static Task createNewTask(String title, String description, PriorityLevel priorityLevel, int reminderTimeYear,
                                     int reminderTimeMonth, int reminderTimeDay, int reminderTimeHour, int reminderTimeMinute,
                                     int reminderTimeSecond) {
        LocalDateTime time = LocalDateTime.of(reminderTimeYear, reminderTimeMonth, reminderTimeDay, reminderTimeHour,
                reminderTimeMinute, reminderTimeSecond);
        return new Task(title, description, priorityLevel, time.toString());
    }

    public LocalDateTime getReminderLocalDateTime() {
        return LocalDateTime.parse(reminderTime);
    }

    public void setReminderLocalDateTime(LocalDateTime time) {
        this.reminderTime = time.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
}