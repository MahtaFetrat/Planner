package com.example.planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Task implements Comparable<Task> {

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

    @Override
    public int compareTo(Task task) {
        LocalDateTime thisTime = LocalDateTime.parse(this.reminderTime);
        LocalDateTime taskTime = LocalDateTime.parse(task.reminderTime);
        if (thisTime.getYear() == taskTime.getYear()) {
            if (thisTime.getMonthValue() == taskTime.getMonthValue()) {
                if (thisTime.getDayOfMonth() == taskTime.getDayOfMonth()) {
                    if (thisTime.getHour() == taskTime.getHour()) {
                        if (thisTime.getMinute() == taskTime.getMinute()) {
                            if (thisTime.getSecond() == taskTime.getSecond()) {
                                if (this.priorityLevel == task.priorityLevel) {
                                    return 0;
                                } else if (this.priorityLevel == PriorityLevel.ESSENTIAL || task.priorityLevel == PriorityLevel.REGULAR) {
                                    return 1;
                                } else return -1;
                            } else {
                                return taskTime.getSecond() - thisTime.getSecond();
                            }
                        } else {
                            return taskTime.getMinute() - thisTime.getMinute();
                        }
                    } else {
                        return taskTime.getHour() - thisTime.getHour();
                    }
                } else {
                    return taskTime.getDayOfMonth() - thisTime.getDayOfMonth();
                }
            } else {
                return taskTime.getMonthValue() - thisTime.getMonthValue();
            }
        } else {
            return taskTime.getYear() - thisTime.getYear();
        }
    }
}