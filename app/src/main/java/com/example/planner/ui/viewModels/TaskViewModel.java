package com.example.planner.ui.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.planner.data.Repository;
import com.example.planner.model.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel (Application application) {
        super(application);
        repository = Repository.getInstance(application);
        allTasks = repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    public void insert(Task task) {
        repository.insertTask(task);
    }
    public void deleteWord(Task task) {
        repository.deleteTask(task);
    }
}