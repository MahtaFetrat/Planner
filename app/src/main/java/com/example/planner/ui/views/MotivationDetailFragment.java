package com.example.planner.ui.views;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.planner.R;
import com.example.planner.model.Motivation;
import com.example.planner.ui.viewModels.TaskViewModel;


public class MotivationDetailFragment extends DialogFragment {

    TaskViewModel viewModel;
    Motivation motivation;

    TextView motivationDetailTextView;

    AppCompatImageButton motivationDetailDeleteButton;
    AppCompatImageButton exportMotivationButton;
    AppCompatImageButton shareMotivationButton;

    public MotivationDetailFragment() {
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
        return inflater.inflate(R.layout.fragment_motivation_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        int motivationId = getArguments().getInt("motivationId");
        motivation = viewModel.getMotivationById(motivationId);

        findViewsById(view);
        setTaskDetailFieldValues();
        setOnClickListeners();
    }

    private void findViewsById(View view) {
        motivationDetailTextView = view.findViewById(R.id.motivationDetailTextView);

        motivationDetailDeleteButton = view.findViewById(R.id.motivationDetailDeleteButton);
        exportMotivationButton = view.findViewById(R.id.exportMotivationButton);
        shareMotivationButton = view.findViewById(R.id.shareMotivationButton);
    }

    private void setTaskDetailFieldValues() {
        motivationDetailTextView.setText(motivation.getSentence());
    }

    private void setOnClickListeners() {
        motivationDetailDeleteButton.setOnClickListener(view -> {deleteMotivation();});
        shareMotivationButton.setOnClickListener(view -> {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Motivational Sentence");
            intent.putExtra(Intent.EXTRA_TEXT, motivation.getSentence());

            startActivity(intent.createChooser(intent, "Share Motivation"));
        });
    }

    private void deleteMotivation() {
        viewModel.deleteMotivation(motivation);
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