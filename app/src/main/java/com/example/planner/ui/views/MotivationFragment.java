package com.example.planner.ui.views;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.planner.R;
import com.example.planner.model.Motivation;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.example.planner.ui.views.Adapters.DailyTaskAdapter;
import com.example.planner.ui.views.Adapters.MotivationAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MotivationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotivationFragment extends Fragment {
    private View root;

    private TaskViewModel viewModel;

    private Button addMotivation;
    private TextInputEditText motivationText;
    private RecyclerView motivations;
    private MotivationAdapter motivationAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MotivationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MotivationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MotivationFragment newInstance(String param1, String param2) {
        MotivationFragment fragment = new MotivationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewsById();
        setOnClick();
        initializeListAdapter();
        setViewModelObservers();

    }

    private void setViewModelObservers() {
        viewModel.getAllMotivations().observe(this, allMotivations -> {
            motivationAdapter.updateList(allMotivations);
        });
    }

    private void initializeListAdapter() {
        motivationAdapter = new MotivationAdapter(this.getActivity(), new ArrayList<>());
        motivations.setAdapter(motivationAdapter);
        motivations.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void setOnClick() {
        addMotivation.setOnClickListener(view -> {
            viewModel.insertMotivation(new Motivation(String.valueOf(motivationText.getText()), ""));
        });
    }

    private void findViewsById() {
        addMotivation = root.findViewById(R.id.addMotivationButton);
        motivationText = root.findViewById(R.id.motivationTextView);
        motivations = root.findViewById(R.id.motivationRecyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_motivation, container, false);
        return root;
    }
}