package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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

        createNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.taskActivity, new CreateTaskFragment()).commit();
            }
        });

        setViewModelObservers();
    }

    private void setViewModelObservers() {
        viewModel.getAllTasks().observe(this, allTasks -> {
            TasksAdapter tasksAdapter = new TasksAdapter(this, allTasks);
            tasks.setAdapter(tasksAdapter);
            tasks.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}
