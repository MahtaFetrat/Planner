package com.example.planner.ui.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.planner.R;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.example.planner.ui.views.Adapters.DailyTaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DailyTaskFragment extends Fragment {
    private TaskViewModel viewModel;

//    private TimePickerDialog startTimePickerDialog;
//    private TimePickerDialog endTimePickerDialog;

//    private Button addDailyTask;
//    private Button startTime;
//    private Button endTime;
//    private TextInputEditText dailyTaskName;
    private RecyclerView dailyTasks;
    private DailyTaskAdapter dailyTaskAdapter;
    private FloatingActionButton createDailyTaskButton;

//    private int startHour, startMinute, endHour, endMinute;

    public DailyTaskFragment() {
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
        return inflater.inflate(R.layout.fragment_daily_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        findViewsById(view);
        setOnClickListeners();
        initStartTimePicker();
        initEndTimePicker();
        initializeListAdapter();
        setViewModelObservers();
    }

    private void findViewsById(View view) {
//        addDailyTask = view.findViewById(R.id.addDailyTaskButton);
//        startTime = view.findViewById(R.id.startTimeButton);
//        endTime = view.findViewById(R.id.endTimeButton);
//        dailyTaskName = view.findViewById(R.id.dailyTaskNameTextInputEditText);
        dailyTasks = view.findViewById(R.id.dailyTasksRecyclerView);
        createDailyTaskButton = view.findViewById(R.id.createNewDailyTaskButton);
    }

    private void setOnClickListeners() {
        createDailyTaskButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CreateDailyTaskActivity.class);
            startActivity(intent);
        });
//        addDailyTask.setOnClickListener(view -> {
//            String newDailyTaskName = String.valueOf(dailyTaskName.getText());
//
//            DailyTask newDailyTask = DailyTask.createNewDailyTask(newDailyTaskName, "", startHour, startMinute, 0, endHour, endMinute, 0, false);
//            viewModel.insertDaily(newDailyTask);
//        });
//
//        startTime.setOnClickListener(view -> startTimePickerDialog.show());
//        endTime.setOnClickListener(view -> endTimePickerDialog.show());
    }

    private void initializeListAdapter() {
        dailyTaskAdapter = new DailyTaskAdapter(getActivity(), new ArrayList<>());
        dailyTasks.setAdapter(dailyTaskAdapter);
        dailyTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setViewModelObservers() {
        viewModel.getAllDailyTasks().observe(getActivity(), allDailyTasks -> {
            dailyTaskAdapter.updateList(allDailyTasks);
        });
    }

    private void initStartTimePicker() {
//        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
//            startTime.setText(String.format(Locale.getDefault(), "%02d:%02d", h, m));
//            startHour = h;
//            startMinute = m;
//        };
//
//        startTimePickerDialog = new TimePickerDialog(getActivity(), timeSetListener, startHour, startMinute, true);
//        startTimePickerDialog.setTitle("Start");
    }

    private void initEndTimePicker() {
//        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
//            endTime.setText(String.format(Locale.getDefault(), "%02d:%02d", h, m));
//            endHour = h;
//            endMinute = m;
//        };
//
//        endTimePickerDialog = new TimePickerDialog(getActivity(), timeSetListener, endHour, endMinute, true);
//        endTimePickerDialog.setTitle("End");
    }
}