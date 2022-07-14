package com.example.planner.ui.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planner.R;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    DatePickerDialog datePickerDialog;
    TextInputEditText newTaskName;
    TextInputEditText newTaskDescription;
    Button newTaskDueDate;
    Button newTaskReminder;
    Button newTaskPriority;
    Button createNewTask;
    int hour, minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

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
        String[] taskDueDate = String.valueOf(newTaskDueDate.getText()).split(" ");
        String[] taskReminder = String.valueOf(newTaskReminder.getText()).split(":");
        PriorityLevel taskPriority = getPriority(String.valueOf(newTaskPriority.getText()));

        Task.createNewTask(taskName, taskDescription, taskPriority, Integer.parseInt(taskDueDate[2]), getMonthInt(taskDueDate[0]), Integer.parseInt(taskDueDate[1]), Integer.parseInt(taskReminder[0]), Integer.parseInt(taskReminder[1]), 0);

        Intent intent = new Intent(CreateTaskActivity.this, TaskActivity.class);
        startActivity(intent);
    }

    private PriorityLevel getPriority(String priority) {
        switch (priority) {
            case "Regular":
                return PriorityLevel.REGULAR;
            case "Important":
                return PriorityLevel.IMPORTANT;
            case "Essential":
                return PriorityLevel.ESSENTIAL;
            default:
                return null;
        }
    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = makeDateString(day, month, year);
            newTaskDueDate.setText(date);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthString(month) + " " + day + " " + year;
    }

    private String getMonthString(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "";
        }
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
                return true;
            case R.id.important:
                newTaskPriority.setText("Important");
                return true;
            case R.id.essential:
                newTaskPriority.setText("Essential");
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
