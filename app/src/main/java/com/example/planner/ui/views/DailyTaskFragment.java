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

    private RecyclerView dailyTasks;
    private DailyTaskAdapter dailyTaskAdapter;
    private FloatingActionButton createDailyTaskButton;

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
        initializeListAdapter();
        setViewModelObservers();
    }

    private void findViewsById(View view) {
        dailyTasks = view.findViewById(R.id.dailyTasksRecyclerView);
        createDailyTaskButton = view.findViewById(R.id.createNewDailyTaskButton);
    }

    private void setOnClickListeners() {
        createDailyTaskButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CreateDailyTaskActivity.class);
            startActivity(intent);
        });
    }

    private void initializeListAdapter() {
        dailyTaskAdapter = new DailyTaskAdapter(getActivity(), new ArrayList<>(), viewModel);
        dailyTasks.setAdapter(dailyTaskAdapter);
        dailyTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setViewModelObservers() {
        viewModel.getAllDailyTasks().observe(getActivity(), allDailyTasks -> {
            dailyTaskAdapter.updateList(allDailyTasks);
        });
    }
}