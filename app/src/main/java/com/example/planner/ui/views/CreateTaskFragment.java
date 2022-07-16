package com.example.planner.ui.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.planner.R;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateTaskFragment extends DialogFragment {
    private TaskViewModel viewModel;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private TextInputEditText taskNameEditText;
    private TextInputEditText taskDescriptionEditText;
    private AppCompatImageButton dueDateButton;
    private AppCompatImageButton dueTimeButton;
    private RatingBar priorityRatingBar;
    private Button createTaskButton;
    private Button cancelAddTaskButton;

    private int year, month, day, hour, minute;
    private PriorityLevel taskPriority;

    private final SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        findViewsById(view);
        initDatePicker();
        initTimePicker();
        setOnClickListeners();
    }

    private void findViewsById(View view) {
        taskNameEditText = view.findViewById(R.id.newTaskNameTextInputEditText);
        taskDescriptionEditText = view.findViewById(R.id.newTaskDescriptionTextInputEditText);
        dueDateButton = view.findViewById(R.id.taskDateInput);
        dueTimeButton = view.findViewById(R.id.taskTimeInput);
        priorityRatingBar = view.findViewById(R.id.taskPriorityInput);
        createTaskButton = view.findViewById(R.id.confirmAddTaskButton);
        cancelAddTaskButton = view.findViewById(R.id.cancelAddTaskButton);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, y, m, d) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            year = y;
            month = m;
            day = d;
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            hour = h;
            minute = m;
        };

        timePickerDialog = new TimePickerDialog(getActivity(), timeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Reminder");
    }

    private void setOnClickListeners() {
        dueDateButton.setOnClickListener(view -> {datePickerDialog.show();});
        dueTimeButton.setOnClickListener(view -> {timePickerDialog.show();});
        priorityRatingBar.setOnClickListener(view -> {setPriorityFromRatingBar((RatingBar) view);});
        createTaskButton.setOnClickListener(view -> {createTask();});
        cancelAddTaskButton.setOnClickListener(view -> {dismiss();});
    }

    private void setPriorityFromRatingBar(RatingBar ratingBar) {
        Toast.makeText(getActivity(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
        int index = ratingBar.getRating() == 0 ? 0 : (int) (ratingBar.getRating() - 1);
        taskPriority = PriorityLevel.values()[index];
    }

    private void createTask() {
        String name = String.valueOf(this.taskNameEditText.getText());
        String description = String.valueOf(taskDescriptionEditText.getText());

        Task newTask = Task.createNewTask(name, description, taskPriority, year, month, day, hour, minute, 0);
        viewModel.insert(newTask);

        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}