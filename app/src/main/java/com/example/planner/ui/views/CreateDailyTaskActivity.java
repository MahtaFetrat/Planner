package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.planner.R;
import com.example.planner.model.DailyTask;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class CreateDailyTaskActivity extends AppCompatActivity {

    TaskViewModel viewModel;

    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    private TextInputLayout taskNameInputLayout;
    private TextInputEditText taskNameEditText;
    private TextInputEditText taskDescriptionEditText;

    private Button startTimeButton;
    private Button endTimeButton;

    private int startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_daily_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        findViewsById();
        initStartTimePicker();
        initEndTimePicker();
        setEventListeners();
    }

    private void findViewsById() {
        taskNameInputLayout = findViewById(R.id.dailyTaskNameInputLayout);
        taskNameEditText = findViewById(R.id.dailyTaskNameTextInputEditText);
        taskDescriptionEditText = findViewById(R.id.dailyTaskDescriptionTextInputEditText);
        startTimeButton = findViewById(R.id.dailyTaskStartTimeInput);
        endTimeButton = findViewById(R.id.dailyTaskEndTimeInput);
    }

    private void initStartTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            startHour = h;
            startMinute = m;
        };

        Calendar calendar = Calendar.getInstance();
        startHour = calendar.get(Calendar.HOUR);
        startMinute = calendar.get(Calendar.MINUTE);

        startTimePickerDialog = new TimePickerDialog(this, timeSetListener, startHour, startMinute, true);
        startTimePickerDialog.setTitle("Pick when you wish to start this task");
    }

    private void initEndTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            endHour = h;
            endMinute = m;
        };

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        endHour = calendar.get(Calendar.HOUR);
        endMinute = calendar.get(Calendar.MINUTE);

        endTimePickerDialog = new TimePickerDialog(this, timeSetListener, endHour, endMinute, true);
        endTimePickerDialog.setTitle("Pick when you wish to finish this task");
    }

    private void setEventListeners() {
        startTimeButton.setOnClickListener(view -> {startTimePickerDialog.show();});
        endTimeButton.setOnClickListener(view -> {endTimePickerDialog.show();});
    }

    private boolean createTask() {
        if (validateTaskFrom()) {
            String name = String.valueOf(this.taskNameEditText.getText());
            String description = String.valueOf(taskDescriptionEditText.getText());

            DailyTask newTask = DailyTask.createNewDailyTask(name, description, startHour, startMinute, 0, endHour, endMinute, 0, false);
            viewModel.insertDaily(newTask);

            return true;
        }
        return false;
    }

    private boolean validateTaskFrom() {
        if (taskNameEditText.getText().toString().isEmpty()) {
            taskNameInputLayout.setError(getResources().getString(R.string.task_title_required_error));
            taskNameInputLayout.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm_form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.confirm_form_item) {
            if (createTask()) {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}