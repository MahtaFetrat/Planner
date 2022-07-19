package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;

import com.example.planner.R;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity {
    private TaskViewModel viewModel;

    private DatePickerDialog dueDatePickerDialog;
    private TimePickerDialog dueTimePickerDialog;

    private DatePickerDialog reminderDatePickerDialog;
    private TimePickerDialog reminderTimePickerDialog;

    private TextInputLayout taskNameInputLayout;
    private TextInputEditText taskNameEditText;
    private TextInputEditText taskDescriptionEditText;
    private MaterialButton dueDateButton;
    private MaterialButton dueTimeButton;
    private RatingBar priorityRatingBar;
    private CheckBox setReminderCheckBox;
    private MaterialButton reminderDateButton;
    private MaterialButton reminderTimeButton;

    private int dueYear = -1, dueMonth = -1, dueDay = -1, dueHour = 0, dueMinute = 0;
    private int reminderYear = -1, reminderMonth = -1, reminderDay = -1, reminderHour = 0, reminderMinute = 0;
    private PriorityLevel taskPriority = PriorityLevel.REGULAR;
    private boolean hasReminder = false;

    private final SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        findViewsById();
        initDueDatePicker();
        initDueTimePicker();
        initReminderDatePicker();
        initReminderTimePicker();
        initRatingBar();
        setEventListeners();
    }


    private void findViewsById() {
        taskNameInputLayout = findViewById(R.id.taskNameInputLayout);
        taskNameEditText = findViewById(R.id.newTaskNameTextInputEditText);
        taskDescriptionEditText = findViewById(R.id.newTaskDescriptionTextInputEditText);
        dueDateButton = findViewById(R.id.taskDateInput);
        dueTimeButton = findViewById(R.id.taskTimeInput);
        priorityRatingBar = findViewById(R.id.taskPriorityInput);
        setReminderCheckBox = findViewById(R.id.reminder);
        reminderDateButton = findViewById(R.id.reminderDateInput);
        reminderTimeButton = findViewById(R.id.reminderTimeInput);
    }

    private void initDueDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, y, m, d) -> {
            dueYear = y;
            dueMonth = m + 1;
            dueDay = d;
            Calendar calendar = Calendar.getInstance();
            calendar.set(dueYear, dueMonth, dueDay, dueHour, dueMinute);
            dueDateButton.setText(getResources().getString(R.string.dueDateFormatSpecifier, formatter.format(calendar.getTime())));
        };

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        dueYear = calendar.get(Calendar.YEAR);
        dueMonth = calendar.get(Calendar.MONTH);
        dueDay = calendar.get(Calendar.DAY_OF_MONTH);
        dueDateButton.setText(getResources().getString(R.string.dueDateFormatSpecifier, formatter.format(calendar.getTime())));

        dueDatePickerDialog = new DatePickerDialog(this, dateSetListener,dueYear, dueMonth, dueDay);
        dueDatePickerDialog.setTitle("Pick a due date for this task");
    }

    private void initDueTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            dueHour = h;
            dueMinute = m;
            dueTimeButton.setText(getResources().getString(R.string.dueTimeFormatSpecifier, String.format(Locale.getDefault(), "%02d:%02d", dueHour, dueMinute)));
        };

        Calendar calendar = Calendar.getInstance();
        dueHour = calendar.get(Calendar.HOUR);
        dueMinute = calendar.get(Calendar.MINUTE);
        dueTimeButton.setText(getResources().getString(R.string.dueTimeFormatSpecifier, String.format(Locale.getDefault(), "%02d:%02d", dueHour, dueMinute)));

        dueTimePickerDialog = new TimePickerDialog(this, timeSetListener, dueHour, dueMinute, true);
        dueTimePickerDialog.setTitle("Pick a due time for this task");
    }

    private void initReminderDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, y, m, d) -> {
            reminderYear = y;
            reminderMonth = m + 1;
            reminderDay = d;
            Calendar calendar = Calendar.getInstance();
            calendar.set(reminderYear, reminderMonth, reminderDay, reminderHour, reminderMinute);
            reminderDateButton.setText(getResources().getString(R.string.reminderDateFormatSpecifier, formatter.format(calendar.getTime())));
        };

        Calendar calendar = Calendar.getInstance();
        calendar.set(dueYear, dueMonth, dueDay, dueHour, dueMinute);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        reminderYear = calendar.get(Calendar.YEAR);
        reminderMonth = calendar.get(Calendar.MONTH);
        reminderDay = calendar.get(Calendar.DAY_OF_MONTH);
        reminderDateButton.setText(getResources().getString(R.string.reminderDateFormatSpecifier, formatter.format(calendar.getTime())));

        reminderDatePickerDialog = new DatePickerDialog(this, dateSetListener, reminderYear, reminderMonth, reminderDay);
        reminderDatePickerDialog.setTitle("Pick a reminder date for this task");
    }

    private void initReminderTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            reminderHour = h;
            reminderMinute = m;
            reminderTimeButton.setText(getResources().getString(R.string.reminderTimeFormatSpecifier, String.format(Locale.getDefault(), "%02d:%02d", reminderHour, reminderMinute)));
        };

        reminderHour = dueHour;
        reminderMinute = dueMinute;
        reminderTimeButton.setText(getResources().getString(R.string.reminderTimeFormatSpecifier, String.format(Locale.getDefault(), "%02d:%02d", reminderHour, reminderMinute)));

        reminderTimePickerDialog = new TimePickerDialog(this, timeSetListener, reminderHour, reminderMinute, true);
        reminderTimePickerDialog.setTitle("Pick a reminder time for this task");
    }

    private void initRatingBar() {
        priorityRatingBar.setRating(1);
        priorityRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (rating < 1) {
                ratingBar.setRating(1);
            }
            taskPriority = PriorityLevel.values()[(int) (ratingBar.getRating() - 1)];
        });
    }

    private void setEventListeners() {
        dueDateButton.setOnClickListener(view -> {dueDatePickerDialog.show();});
        dueTimeButton.setOnClickListener(view -> {dueTimePickerDialog.show();});
        reminderDateButton.setOnClickListener(view -> {reminderDatePickerDialog.show();});
        reminderTimeButton.setOnClickListener(view -> {reminderTimePickerDialog.show();});
        setReminderCheckBox.setOnClickListener(view -> {
            hasReminder = setReminderCheckBox.isChecked();
            reminderDateButton.setVisibility(hasReminder ? View.VISIBLE : View.INVISIBLE);
            reminderTimeButton.setVisibility(hasReminder ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private boolean createTask() {
        if (validateTaskFrom()) {
            String name = String.valueOf(this.taskNameEditText.getText());
            String description = String.valueOf(taskDescriptionEditText.getText());

            Task newTask = Task.createNewTask(name, description, taskPriority, dueYear, dueMonth, dueDay, dueHour, dueMinute, 0, hasReminder,
                    reminderYear, reminderMonth, reminderDay, reminderHour, reminderMinute, 0);
            viewModel.insert(newTask);
            if (newTask.getHasReminder()) {
                setReminder(newTask);
            }
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

    private void setReminder(Task task) {
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
        intent.putExtra("taskId", task.getId());
        intent.putExtra("taskTitle", task.getTitle());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        LocalDateTime time = task.getReminderLocalDateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), time.getHour(), time.getMinute(), 0);

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}