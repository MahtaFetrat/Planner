package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.planner.R;
import com.example.planner.model.Task;
import com.example.planner.ui.viewModels.TaskViewModel;

public class TaskActivity extends AppCompatActivity {
    private TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        setViewModelObservers();
    }

    private void setViewModelObservers() {
        viewModel.getAllTasks().observe(this, allTasks -> {
            //set list items with this list of allTasks
            //for example titleTextView.setText(allTasks[0].getTitle());
        });
    }
}
