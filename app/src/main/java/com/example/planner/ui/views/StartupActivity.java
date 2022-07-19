package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.planner.R;
import com.example.planner.model.Motivation;
import com.example.planner.ui.viewModels.TaskViewModel;


public class StartupActivity extends AppCompatActivity {

    ConstraintLayout startupLayout;
    TextView startupSentenceTextView;
    TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        findViewsById();
        setOnClickListeners();
        setStartupSentence();
    }

    private void findViewsById() {
        startupLayout = findViewById(R.id.startupLayout);
        startupSentenceTextView = findViewById(R.id.startupSentenceTextView);
    }

    private void setOnClickListeners() {
        startupLayout.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void setStartupSentence() {
        viewModel.getAllMotivations().observe(this, motivations -> {
            if (!motivations.isEmpty()) {
                int randomIndex = (int)(Math.random() * motivations.size());
                Motivation motivation = motivations.get(randomIndex);
                startupSentenceTextView.setText(motivation.getSentence());
            }
        });
    }
}