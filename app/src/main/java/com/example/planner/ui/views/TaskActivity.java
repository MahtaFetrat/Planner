package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.planner.R;
import com.example.planner.ui.viewModels.TaskViewModel;

public class TaskActivity extends AppCompatActivity {
    private TaskViewModel viewModel;
    private Button createNewTask;
    private RecyclerView tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        createNewTask = findViewById(R.id.createNewTaskButton);
        tasks = findViewById(R.id.tasksRecyclerView);

        setViewModelObservers();
    }

    private void setViewModelObservers() {
        viewModel.getAllTasks().observe(this, allTasks -> {
            TasksAdapter tasksAdapter = new TasksAdapter(this, allTasks);
            tasks.setAdapter(tasksAdapter);
            tasks.setLayoutManager(new LinearLayoutManager(this));
            //set list items with this list of allTasks
            //for example titleTextView.setText(allTasks[0].getTitle());
        });
    }
}
