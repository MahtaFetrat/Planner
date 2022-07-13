package com.example.planner.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class DailyTask implements Comparable<DailyTask> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private String startTime;

    @ColumnInfo
    private String endTime;

    @ColumnInfo
    private boolean isDone;

    public DailyTask(String title, String description, String startTime, String endTime, boolean isDone) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDone = isDone;
    }

    public static DailyTask createNewDailyTask(String title, String description, int startTimeHour, int startTimeMinute,
                                               int startTimeSecond, int endTimeHour, int endTimeMinute, int endTimeSecond,
                                               boolean isDone) {
        LocalTime start = LocalTime.of(startTimeHour, startTimeMinute, startTimeSecond);
        LocalTime end = LocalTime.of(endTimeHour, endTimeMinute, endTimeSecond);
        return new DailyTask(title, description, start.toString(), end.toString(), isDone);
    }

    public LocalTime getStartLocalTime() {
        return LocalTime.parse(startTime);
    }

    public void setStartLocalTime(LocalTime time) {
        this.startTime = time.toString();
    }

    public LocalTime getEndLocalTime() {
        return LocalTime.parse(endTime);
    }

    public void setEndLocalTime(LocalTime time) {
        this.endTime = time.toString();
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public int compareTo(DailyTask dailyTask) {
        LocalTime thisTime = LocalTime.parse(this.startTime);
        LocalTime taskTime = LocalTime.parse(dailyTask.startTime);
        if (thisTime.getHour() == taskTime.getHour()) {
            if (thisTime.getMinute() == taskTime.getMinute()) {
                if (thisTime.getSecond() == taskTime.getSecond()) {
                    return 0;
                } else {
                    return taskTime.getSecond() - thisTime.getSecond();
                }
            } else {
                return taskTime.getMinute() - thisTime.getMinute();
            }
        } else {
            return taskTime.getHour() - thisTime.getHour();
        }
    }
}
