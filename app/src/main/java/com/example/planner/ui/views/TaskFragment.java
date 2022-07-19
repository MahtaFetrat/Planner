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
import com.example.planner.ui.views.Adapters.TasksAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TaskFragment extends Fragment {
    private TaskViewModel viewModel;

    public static FloatingActionButton createNewTask;
    private RecyclerView tasksRecyclerView;

    private TasksAdapter tasksAdapter;
    private LinearLayoutManager taskListLayoutManager;

    private MaterialCardView listShadow;

    public TaskFragment() {
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
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        findViewsById(view);
        initializeListAdapter();
        setOnClickListeners();
        setViewModelObservers();
    }

    private void findViewsById(View view) {
        createNewTask = view.findViewById(R.id.createNewTaskButton);
        tasksRecyclerView = view.findViewById(R.id.tasksRecyclerView);
        listShadow = view.findViewById(R.id.listShadow);
    }

    private void initializeListAdapter() {
        tasksAdapter = new TasksAdapter(getActivity(), new ArrayList<>());
        tasksRecyclerView.setAdapter(tasksAdapter);
        taskListLayoutManager = new LinearLayoutManager(getActivity());
        taskListLayoutManager.setReverseLayout(true);
        tasksRecyclerView.setLayoutManager(taskListLayoutManager);
    }

    private void setOnClickListeners() {
        createNewTask.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
            startActivity(intent);
        });
    }

    private void setViewModelObservers() {
        viewModel.getAllTasks().observe(getViewLifecycleOwner(), allTasks -> {
            listShadow.setVisibility(allTasks.isEmpty() ? View.INVISIBLE : View.VISIBLE);
            tasksAdapter.updateList(allTasks);
            taskListLayoutManager.scrollToPosition(allTasks.size() - 1);
        });
    }
}