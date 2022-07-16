package com.example.planner.ui.views;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planner.R;
import com.example.planner.model.DailyTask;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

public class DailyTaskActivity extends AppCompatActivity {
    private TaskViewModel viewModel;

    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    private Button addDailyTask;
    private Button startTime;
    private Button endTime;
    private TextInputEditText dailyTaskName;
    private RecyclerView dailyTasks;
    private DailyTaskAdapter dailyTaskAdapter;

    private int startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        findViewsById();
        setOnClick();
        initStartTimePicker();
        initEndTimePicker();
        initializeListAdapter();
        setViewModelObservers();
    }

    private void findViewsById() {
        addDailyTask = findViewById(R.id.addDailyTaskButton);
        startTime = findViewById(R.id.startTimeButton);
        endTime = findViewById(R.id.endTimeButton);
        dailyTaskName = findViewById(R.id.dailyTaskNameTextInputEditText);
        dailyTasks = findViewById(R.id.dailyTasksRecyclerView);
    }

    private void setOnClick() {
        addDailyTask.setOnClickListener(view -> {
            String newDailyTaskName = String.valueOf(dailyTaskName.getText());

            DailyTask newDailyTask = DailyTask.createNewDailyTask(newDailyTaskName, "", startHour, startMinute, 0, endHour, endMinute, 0, false);
            viewModel.insertDaily(newDailyTask);
        });

        startTime.setOnClickListener(view -> startTimePickerDialog.show());
        endTime.setOnClickListener(view -> endTimePickerDialog.show());
    }

    private void initializeListAdapter() {
        dailyTaskAdapter = new DailyTaskAdapter(this, new ArrayList<>());
        dailyTasks.setAdapter(dailyTaskAdapter);
        dailyTasks.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setViewModelObservers() {
        viewModel.getAllDailyTasks().observe(this, allDailyTasks -> {
            dailyTaskAdapter.updateList(allDailyTasks);
        });
    }

    private void initStartTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            startTime.setText(String.format(Locale.getDefault(), "%02d:%02d", h, m));
            startHour = h;
            startMinute = m;
        };

        startTimePickerDialog = new TimePickerDialog(this, timeSetListener, startHour, startMinute, true);
        startTimePickerDialog.setTitle("Start");
    }

    private void initEndTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            endTime.setText(String.format(Locale.getDefault(), "%02d:%02d", h, m));
            endHour = h;
            endMinute = m;
        };

        endTimePickerDialog = new TimePickerDialog(this, timeSetListener, endHour, endMinute, true);
        endTimePickerDialog.setTitle("End");
    }
}
