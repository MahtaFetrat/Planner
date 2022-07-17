package com.example.planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

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
    private String dueTime;

    @ColumnInfo
    private Boolean hasReminder;

    @ColumnInfo
    private String reminderTime;

    public Task(String title, String description, PriorityLevel priorityLevel, String dueTime, boolean hasReminder, String reminderTime) {
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.dueTime = dueTime;
        this.hasReminder = hasReminder;
        this.reminderTime = reminderTime;
    }

    public static Task createNewTask(String title, String description, PriorityLevel priorityLevel,
                                     int dueYear, int dueMonth, int dueDay, int dueHour, int dueMinute, int dueSecond, boolean hasReminder,
                                     int reminderYear, int reminderMonth, int reminderDay, int reminderHour, int reminderMinute, int reminderSecond) {
        String dueTimeString = LocalDateTime.of(dueYear, dueMonth, dueDay, dueHour, dueMinute, dueSecond).toString();
        String reminderTimeString = LocalDateTime.of(reminderYear, reminderMonth, reminderDay, reminderHour, reminderMinute, reminderSecond).toString();
        return new Task(title, description, priorityLevel, dueTimeString, hasReminder, reminderTimeString);
    }

    public LocalDateTime getDueDateLocalDateTime() {
        return LocalDateTime.parse(dueTime);
    }

    public LocalDateTime getReminderLocalDateTime() {
        return LocalDateTime.parse(reminderTime);
    }

    public void setDueDateLocalDateTime(LocalDateTime time) {
        this.dueTime = time.toString();
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

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public Boolean getHasReminder() {
        return hasReminder;
    }

    public void setHasReminder(Boolean hasReminder) {
        this.hasReminder = hasReminder;
    }

    @Override
    public int compareTo(Task task) {
        LocalDateTime thisTime = LocalDateTime.parse(this.dueTime);
        LocalDateTime taskTime = LocalDateTime.parse(task.dueTime);
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