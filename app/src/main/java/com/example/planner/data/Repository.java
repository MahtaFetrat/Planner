package com.example.planner.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.planner.db.PlannerDatabase;
import com.example.planner.model.DailyTask;
import com.example.planner.model.Motivation;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Repository {
    private static Repository instance;
    private PlannerDatabase db;

    private LiveData<List<Task>> allTasks;
    private LiveData<List<DailyTask>> allDailyTasks;
    private LiveData<List<Motivation>> allMotivations;

    Repository(Application application) {
        db = PlannerDatabase.getINSTANCE(application);

        allTasks = db.plannerDao().getAllTasks();
        allDailyTasks = db.plannerDao().getAllDailyTasks();
        allMotivations = db.plannerDao().getAllMotivations();
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
        db.plannerDao().insert(task);
//        List<Task> tasks = db.plannerDao().getAllTasks();
//        Collections.sort(tasks);
//        allTasks = new MutableLiveData<>(tasks);
    }

    public void insertDailyTask(DailyTask dailyTask) {
        db.plannerDao().insert(dailyTask);
//        List<DailyTask> dailyTasks = db.plannerDao().getAllDailyTasks();
//        Collections.sort(dailyTasks);
//        allDailyTasks = new MutableLiveData<>(dailyTasks);
    }

    public void insertMotivation(Motivation motivation) {
        db.plannerDao().insert(motivation);
//        allMotivations = new MutableLiveData<>(db.plannerDao().getAllMotivations());
    }

    public void deleteTask(Task task) {
        db.plannerDao().delete(task);
//        List<Task> tasks = db.plannerDao().getAllTasks();
//        Collections.sort(tasks);
//        allTasks = new MutableLiveData<>(tasks);
    }

    public void deleteDailyTask(DailyTask dailyTask) {
        db.plannerDao().delete(dailyTask);
//        List<DailyTask> dailyTasks = db.plannerDao().getAllDailyTasks();
//        Collections.sort(dailyTasks);
//        allDailyTasks = new MutableLiveData<>(dailyTasks);
    }

    public void deleteMotivation(Motivation motivation) {
        db.plannerDao().delete(motivation);
//        allMotivations = new MutableLiveData<>(db.plannerDao().getAllMotivations());
    }
}
