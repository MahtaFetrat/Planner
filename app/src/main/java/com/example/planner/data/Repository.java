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
import java.util.List;

public class Repository {
    private static Repository instance;
    private PlannerDatabase db;

    private MutableLiveData<List<Task>> allTasks;
    private MutableLiveData<List<DailyTask>> allDailyTasks;
    private MutableLiveData<List<Motivation>> allMotivations;

    //Sample data for test:
    ArrayList<Task> allSampleTasks = new ArrayList<>(Arrays.asList(
            new Task("task1", "desc1", PriorityLevel.ESSENTIAL, LocalDateTime.now().toString()),
            new Task("task2", "desc2", PriorityLevel.ESSENTIAL, LocalDateTime.now().toString()),
            new Task("task3", "desc3", PriorityLevel.ESSENTIAL, LocalDateTime.now().toString())
    ));
    ArrayList<DailyTask> allSampleDailyTasks = new ArrayList<>(Arrays.asList(
            new DailyTask("daily_title1", "daily_desc1", LocalTime.now().toString(), LocalTime.now().toString(), true),
            new DailyTask("daily_title2", "daily_desc2", LocalTime.now().toString(), LocalTime.now().toString(), true),
            new DailyTask("daily_title3", "daily_desc3", LocalTime.now().toString(), LocalTime.now().toString(), true)
    ));
    ArrayList<Motivation> allSampleMotivations = new ArrayList<>(Arrays.asList(
            new Motivation("motiv1", "motiv_desc1"),
            new Motivation("motiv2", "motiv_desc2"),
            new Motivation("motiv3", "motiv_desc3")
    ));


    Repository(Application application) {
        db = PlannerDatabase.getINSTANCE(application);

        List<Task> dbTasks = db.plannerDao().getAllTasks();
        List<DailyTask> dbDailyTasks = db.plannerDao().getAllDailyTasks();
        List<Motivation> dbMotivations = db.plannerDao().getAllMotivations();

        allTasks = (dbTasks == null) ? new MutableLiveData<>(new ArrayList<>()) :
                new MutableLiveData<>(db.plannerDao().getAllTasks());
        allDailyTasks = (dbDailyTasks == null) ? new MutableLiveData<>(new ArrayList<>()) :
                new MutableLiveData<>(db.plannerDao().getAllDailyTasks());
        allMotivations = (dbMotivations == null) ? new MutableLiveData<>(new ArrayList<>()) :
                new MutableLiveData<>(db.plannerDao().getAllMotivations());
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
        allTasks = new MutableLiveData<>(db.plannerDao().getAllTasks());
    }

    public void insertDailyTask(DailyTask dailyTask) {
        db.plannerDao().insert(dailyTask);
        allDailyTasks = new MutableLiveData<>(db.plannerDao().getAllDailyTasks());
    }

    public void insertMotivation(Motivation motivation) {
        db.plannerDao().insert(motivation);
        allMotivations = new MutableLiveData<>(db.plannerDao().getAllMotivations());
    }

    public void deleteTask(Task task) {
        db.plannerDao().delete(task);
        allTasks = new MutableLiveData<>(db.plannerDao().getAllTasks());
    }

    public void deleteDailyTask(DailyTask dailyTask) {
        db.plannerDao().delete(dailyTask);
        allDailyTasks = new MutableLiveData<>(db.plannerDao().getAllDailyTasks());
    }

    public void deleteMotivation(Motivation motivation) {
        db.plannerDao().delete(motivation);
        allMotivations = new MutableLiveData<>(db.plannerDao().getAllMotivations());
    }
}
