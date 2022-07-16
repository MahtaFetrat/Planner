package com.example.planner.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.planner.model.DailyTask;
import com.example.planner.model.Motivation;
import com.example.planner.model.Task;

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
    }

    public void insertDailyTask(DailyTask dailyTask) {
        db.plannerDao().insert(dailyTask);
    }

    public void insertMotivation(Motivation motivation) {
        db.plannerDao().insert(motivation);
    }

    public void deleteTask(Task task) {
        db.plannerDao().delete(task);
    }

    public void deleteDailyTask(DailyTask dailyTask) {
        db.plannerDao().delete(dailyTask);
    }

    public void deleteMotivation(Motivation motivation) {
        db.plannerDao().delete(motivation);
    }
}
