package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.planner.R;
import com.example.planner.model.Task;
import com.example.planner.ui.viewModels.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private TaskViewModel viewModel;
    private Button createNewTask;
    private RecyclerView tasks;
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        createNewTask = findViewById(R.id.createNewTaskButton);
        tasks = findViewById(R.id.tasksRecyclerView);


        List<Task> myTasks = new ArrayList<>();
        tasksAdapter = new TasksAdapter(this, myTasks);
        tasks.setAdapter(tasksAdapter);
        tasks.setLayoutManager(new LinearLayoutManager(this));

        createNewTask.setOnClickListener(view -> {
            Intent intent = new Intent(TaskActivity.this, CreateTaskActivity.class);
            startActivity(intent);
        });

        setViewModelObservers();
    }

    private void setViewModelObservers() {
        viewModel.getAllTasks().observe(this, allTasks -> {
            tasksAdapter.updateList(allTasks);
        });
    }
}
