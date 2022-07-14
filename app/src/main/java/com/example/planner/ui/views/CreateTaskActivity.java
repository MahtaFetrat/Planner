package com.example.planner.ui.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.planner.R;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private TaskViewModel viewModel;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private PopupMenu priorityMenu;

    private TextInputEditText taskNameEditText;
    private TextInputEditText taskDescriptionEditText;
    private Button setDueDateButton;
    private Button setReminderButton;
    private Button setPriorityButton;
    private Button createTaskButton;

    private int year, month, day, hour, minute;
    private PriorityLevel taskPriority;

    private final SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        findViewsById();
        initDatePicker();
        initTimePicker();
        initPriorityMenu();
        setOnClickListeners();
    }

    private void findViewsById() {
        taskNameEditText = findViewById(R.id.newTaskNameTextInputEditText);
        taskDescriptionEditText = findViewById(R.id.newTaskDescriptionTextInputEditText);
        setDueDateButton = findViewById(R.id.newTaskDueDateButton);
        setReminderButton = findViewById(R.id.newTaskReminderButton);
        setPriorityButton = findViewById(R.id.newTaskPriorityButton);
        createTaskButton = findViewById(R.id.createTaskButton);
    }

    private void initDatePicker() {
        setDueDateButton.setText(formatter.format(Calendar.getInstance().getTime()));

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, y, m, d) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            setDueDateButton.setText(formatter.format(calendar.getTime()));
            year = y;
            month = m;
            day = d;
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            setReminderButton.setText(String.format(Locale.getDefault(), "%02d:%02d", h, m));
            hour = h;
            minute = m;
        };

        timePickerDialog = new TimePickerDialog(this, timeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Reminder");
    }

    private void initPriorityMenu() {
        priorityMenu = new PopupMenu(this, setPriorityButton);
        priorityMenu.setOnMenuItemClickListener(this);
        priorityMenu.inflate(R.menu.priorties_menu);
    }

    private void setOnClickListeners() {
        setDueDateButton.setOnClickListener(view -> {datePickerDialog.show();});
        setReminderButton.setOnClickListener(view -> {timePickerDialog.show();});
        setPriorityButton.setOnClickListener(view -> {priorityMenu.show();});
        createTaskButton.setOnClickListener(this::createTask);
    }

    private void createTask(View view) {
        String name = String.valueOf(this.taskNameEditText.getText());
        String description = String.valueOf(taskDescriptionEditText.getText());

        Task newTask = Task.createNewTask(name, description, taskPriority, year, month, day, hour, minute, 0);
        viewModel.insert(newTask);

        finish();
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.regular:
                setPriorityButton.setText("Regular");
                taskPriority = PriorityLevel.REGULAR;
                return true;
            case R.id.important:
                setPriorityButton.setText("Important");
                taskPriority = PriorityLevel.IMPORTANT;
                return true;
            case R.id.essential:
                setPriorityButton.setText("Essential");
                taskPriority = PriorityLevel.ESSENTIAL;
                return true;
            default:
                return false;
        }
    }
}
