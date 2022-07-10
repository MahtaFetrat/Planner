package com.example.planner.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.planner.model.DailyTask;
import com.example.planner.model.Motivation;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Repository {
    private static Repository instance;

    private MutableLiveData<List<Task>> allTasks;
    private MutableLiveData<List<DailyTask>> allDailyTasks;
    private MutableLiveData<List<Motivation>> allMotivations;

    //Sample data for test:
    ArrayList<Task> allSampleTasks = new ArrayList<>(Arrays.asList(
            new Task("task1", "desc1", PriorityLevel.ESSENTIAL, LocalDateTime.now()),
            new Task("task2", "desc2", PriorityLevel.ESSENTIAL, LocalDateTime.now()),
            new Task("task3", "desc3", PriorityLevel.ESSENTIAL, LocalDateTime.now())
    ));
    ArrayList<DailyTask> allSampleDailyTasks = new ArrayList<>(Arrays.asList(
            new DailyTask("daily_title1", "daily_desc1", LocalTime.now(), LocalTime.now(), true),
            new DailyTask("daily_title2", "daily_desc2", LocalTime.now(), LocalTime.now(), true),
            new DailyTask("daily_title3", "daily_desc3", LocalTime.now(), LocalTime.now(), true)
    ));
    ArrayList<Motivation> allSampleMotivations = new ArrayList<>(Arrays.asList(
            new Motivation("motiv1", "motiv_desc1"),
            new Motivation("motiv2", "motiv_desc2"),
            new Motivation("motiv3", "motiv_desc3")
    ));


    Repository(Application application) {
        allTasks = new MutableLiveData<>(allSampleTasks);
        allDailyTasks = new MutableLiveData<>(allSampleDailyTasks);
        allMotivations = new MutableLiveData<>(allSampleMotivations);
    }

    public static Repository getInstance(Application application) {
        if (instance == null) {
            instance = new Repository(application);
        }
        return instance;
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<DailyTask>> getAllDailyTasks() {
        return allDailyTasks;
    }

    public LiveData<List<Motivation>> getAllMotivations() {
        return allMotivations;
    }

    public void insertTask(Task task) {
        List<Task> newList = allTasks.getValue();
        newList.add(task);
        allTasks.setValue(newList);
    }

    public void insertDailyTask(DailyTask dailyTask) {
        List<DailyTask> newList = allDailyTasks.getValue();
        newList.add(dailyTask);
        allDailyTasks.setValue(newList);
    }

    public void insertMotivation(Motivation motivation) {
        List<Motivation> newList = allMotivations.getValue();
        newList.add(motivation);
        allMotivations.setValue(newList);
    }

    public void deleteTask(Task task) {
        List<Task> newList = allTasks.getValue();
        newList.remove(task);
        allTasks.setValue(newList);
    }

    public void deleteDailyTask(DailyTask dailyTask) {
        List<DailyTask> newList = allDailyTasks.getValue();
        newList.remove(dailyTask);
        allDailyTasks.setValue(newList);
    }

    public void deleteMotivation(Motivation motivation) {
        List<Motivation> newList = allMotivations.getValue();
        newList.remove(motivation);
        allMotivations.setValue(newList);
    }
}
