package com.example.planner.ui.views;

import android.app.Dialog;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.planner.R;
import com.example.planner.model.DailyTask;
import com.example.planner.ui.viewModels.TaskViewModel;

import java.time.format.DateTimeFormatter;


public class DailyTaskDetailFragment extends DialogFragment {

    TaskViewModel viewModel;
    DailyTask dailyTask;

    TextView taskTitle;
    TextView taskDescription;
    TextView taskStartTime;
    TextView taskEndTime;
    CheckBox taskIsDone;

    AppCompatImageButton taskDetailDeleteButton;

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public DailyTaskDetailFragment() {
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
        return inflater.inflate(R.layout.fragment_daily_task_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        int taskId = getArguments().getInt("taskId");
        dailyTask = viewModel.getDailyTaskViewById(taskId);

        findViewsById(view);
        setTaskDetailFieldValues();
        setOnClickListeners();
    }

    private void findViewsById(View view) {
        taskTitle = view.findViewById(R.id.dailyTaskDetailTitle);
        taskDescription = view.findViewById(R.id.dailyTaskDetailDescription);
        taskStartTime = view.findViewById(R.id.dailyTaskDetailStartTime);
        taskEndTime = view.findViewById(R.id.dailyTaskDetailEndTime);
        taskIsDone = view.findViewById(R.id.dailyTaskDetailIsDone);

        taskDetailDeleteButton = view.findViewById(R.id.dailyTaskDetailDeleteButton);
    }

    private void setTaskDetailFieldValues() {
        taskTitle.setText(dailyTask.getTitle());
        taskDescription.setText(dailyTask.getTitle());
        taskStartTime.setText(timeFormatter.format(dailyTask.getStartLocalTime()));
        taskEndTime.setText(timeFormatter.format(dailyTask.getEndLocalTime()));
        taskIsDone.setChecked(dailyTask.isDone());
        if (dailyTask.isDone()) {
            taskTitle.setPaintFlags(taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            taskTitle.setPaintFlags(0);
        }
    }

    private void setOnClickListeners() {
        taskDetailDeleteButton.setOnClickListener(view -> {deleteTask();});
    }

    private void deleteTask() {
        viewModel.deleteDailyWord(dailyTask);
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