package com.example.planner.ui.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private TaskViewModel viewModel;
    private DatePickerDialog datePickerDialog;
    private TextInputEditText newTaskName;
    private TextInputEditText newTaskDescription;
    private Button newTaskDueDate;
    private Button newTaskReminder;
    private Button newTaskPriority;
    private Button createNewTask;
    private int hour, minute;
    private PriorityLevel taskPriority;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        initDatePicker();

        newTaskName = findViewById(R.id.newTaskNameTextInputEditText);
        newTaskDescription = findViewById(R.id.newTaskDescriptionTextInputEditText);
        newTaskDueDate = findViewById(R.id.newTaskDueDateButton);
        newTaskReminder = findViewById(R.id.newTaskReminderButton);
        newTaskPriority = findViewById(R.id.newTaskPriorityButton);
        createNewTask = findViewById(R.id.createTaskButton);

        newTaskDueDate.setOnClickListener(this::openDatePicker);
        newTaskReminder.setOnClickListener(this::openTimePicker);
        newTaskPriority.setOnClickListener(this::showPriorities);
        createNewTask.setOnClickListener(this::createTask);

        newTaskDueDate.setText(getTodaysDate());
    }

    private void createTask(View view) {
        String taskName = String.valueOf(newTaskName.getText());
        String taskDescription = String.valueOf(newTaskDescription.getText());
        String taskDueDate = String.valueOf(newTaskDueDate.getText());

        Date dueDate = null;
        try {
            dueDate = formatter.parse(taskDueDate);
            Task newTask = Task.createNewTask(taskName, taskDescription, taskPriority, dueDate.getYear() + 1900, dueDate.getMonth(), dueDate.getDay(), hour, minute, 0);
            viewModel.insert(newTask);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(CreateTaskActivity.this, TaskActivity.class);
        startActivity(intent);
    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Date todaysDate = new Date(year - 1900, month, day);
        return formatter.format(todaysDate);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            Date newTaskDate = new Date(year - 1900, month, day);
            newTaskDueDate.setText(formatter.format(newTaskDate));
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private int getMonthInt(String month) {
        switch (month) {
            case "JAN":
                return 1;
            case "FEB":
                return 2;
            case "MAR":
                return 3;
            case "APR":
                return 4;
            case "MAY":
                return 5;
            case "JUN":
                return 6;
            case "JUL":
                return 7;
            case "AUG":
                return 8;
            case "SEP":
                return 9;
            case "OCT":
                return 10;
            case "NOV":
                return 11;
            case "DEC":
                return 12;
            default:
                return 0;
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void openTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            hour = h;
            minute = m;
            newTaskReminder.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, true);
        timePickerDialog.setTitle("00:00");
        timePickerDialog.show();
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.regular:
                newTaskPriority.setText("Regular");
                taskPriority = PriorityLevel.REGULAR;
                return true;
            case R.id.important:
                newTaskPriority.setText("Important");
                taskPriority = PriorityLevel.IMPORTANT;
                return true;
            case R.id.essential:
                newTaskPriority.setText("Essential");
                taskPriority = PriorityLevel.ESSENTIAL;
                return true;
            default:
                return false;
        }
    }

    public void showPriorities(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.priorties_menu);
        popupMenu.show();
    }
}
