package com.example.planner.ui.views;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.planner.R;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;
import com.example.planner.ui.viewModels.TaskViewModel;

import java.time.format.DateTimeFormatter;

public class TaskDetailFragment extends DialogFragment {

    TaskViewModel viewModel;
    Task task;

    TextView taskTitle;
    TextView taskDescription;
    TextView taskDueDate;
    TextView taskDueTime;
    TextView taskReminderDate;
    TextView taskReminderTime;
    RatingBar taskRating;
    LinearLayoutCompat taskDetailReminderDateTimeLayout;

    AppCompatImageButton taskDetailDeleteButton;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        int taskId = getArguments().getInt("taskId");
        task = viewModel.getTaskById(taskId);

        findViewsById(view);
        setTaskDetailFieldValues();
        setOnClickListeners();
    }

    private void findViewsById(View view) {
        taskTitle = view.findViewById(R.id.taskDetailTitle);
        taskDescription = view.findViewById(R.id.taskDetailDescription);
        taskDueDate = view.findViewById(R.id.taskDetailDueDate);
        taskDueTime = view.findViewById(R.id.taskDetailDueTime);
        taskReminderDate = view.findViewById(R.id.taskDetailReminderDate);
        taskReminderTime = view.findViewById(R.id.taskDetailReminderTime);
        taskRating = view.findViewById(R.id.taskDetailRatingBar);
        taskDetailReminderDateTimeLayout = view.findViewById(R.id.taskDetailReminderDateTimeLayout);

        taskDetailDeleteButton = view.findViewById(R.id.taskDetailDeleteButton);
    }

    private void setTaskDetailFieldValues() {
        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getTitle());
        taskDueDate.setText(dateFormatter.format(task.getDueDateLocalDateTime()));
        taskDueTime.setText(timeFormatter.format(task.getDueDateLocalDateTime()));
        if (task.getHasReminder()) {
            taskDetailReminderDateTimeLayout.setVisibility(View.VISIBLE);
            taskReminderDate.setText(dateFormatter.format(task.getReminderLocalDateTime()));
            taskReminderTime.setText(timeFormatter.format(task.getReminderLocalDateTime()));
        }
        PriorityLevel priority = task.getPriorityLevel();
        taskRating.setRating(priority == PriorityLevel.REGULAR ? 1 : priority == PriorityLevel.IMPORTANT ? 2 : 3);
    }

    private void setOnClickListeners() {
        taskDetailDeleteButton.setOnClickListener(view -> {deleteTask();});
    }

    private void deleteTask() {
        viewModel.deleteTask(task);
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int)(size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
    }
}