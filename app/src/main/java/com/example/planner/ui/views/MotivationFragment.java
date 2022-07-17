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
import com.example.planner.model.Motivation;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.example.planner.ui.views.Adapters.MotivationAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MotivationFragment extends Fragment {
    private TaskViewModel viewModel;

    private RecyclerView motivations;
    private FloatingActionButton addMotivationButton;

    private MotivationAdapter motivationAdapter;

    public MotivationFragment() {
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
        return inflater.inflate(R.layout.fragment_motivation, container, false);
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
        motivations = view.findViewById(R.id.motivationRecyclerView);
        addMotivationButton = view.findViewById(R.id.createMotivationButton);
    }

    private void setViewModelObservers() {
        viewModel.getAllMotivations().observe(getViewLifecycleOwner(), allMotivations -> {
            motivationAdapter.updateList(allMotivations);
        });
    }

    private void initializeListAdapter() {
        motivationAdapter = new MotivationAdapter(this.getActivity(), new ArrayList<>());
        motivations.setAdapter(motivationAdapter);
        motivations.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void setOnClickListeners() {
        addMotivationButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CreateMotivationActivity.class);
            startActivity(intent);
        });
    }
}