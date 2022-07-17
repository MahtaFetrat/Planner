package com.example.planner.ui.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.planner.data.Repository;
import com.example.planner.model.DailyTask;
import com.example.planner.model.Motivation;
import com.example.planner.model.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<DailyTask>> allDailyTasks;
    private LiveData<List<Motivation>> allMotivations;

    public TaskViewModel (Application application) {
        super(application);
        repository = Repository.getInstance(application);
        allTasks = repository.getAllTasks();
        allDailyTasks = repository.getAllDailyTasks();
        allMotivations = repository.getAllMotivations();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    public void insert(Task task) {
        repository.insertTask(task);
    }
    public void deleteTask(Task task) {
        repository.deleteTask(task);
    }

    public LiveData<List<DailyTask>> getAllDailyTasks() {
        return allDailyTasks;
    }
    public void insertDaily(DailyTask dailyTask) {
        repository.insertDailyTask(dailyTask);
    }
    public void deleteDailyWord(DailyTask dailyTask) {
        repository.deleteDailyTask(dailyTask);
    }
    public void updateDailyTask(DailyTask dailyTask) { repository.updateDailyTask(dailyTask); }

    public LiveData<List<Motivation>> getAllMotivations() {
        return allMotivations;
    }
    public void insertMotivation(Motivation motivation) {
        repository.insertMotivation(motivation);
    }
    public void deleteMotivation(Motivation motivation) {
        repository.deleteMotivation(motivation);
    }

    public Task getTaskById(int id) { return repository.getTaskById(id); }
}