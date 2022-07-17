package com.example.planner.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.planner.R;
import com.example.planner.model.Motivation;
import com.example.planner.ui.viewModels.TaskViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CreateMotivationActivity extends AppCompatActivity {

    TaskViewModel viewModel;
    TextInputLayout sentenceInputLayout;
    TextInputEditText sentenceInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_motivation);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        findViewsById();
    }

    private void findViewsById() {
        sentenceInputLayout = findViewById(R.id.sentenceInputLayout);
        sentenceInputEditText = findViewById(R.id.sentenceInputEditText);
    }

    private boolean createMotivation() {
        if (validateMotivationForm()) {
            String sentence = String.valueOf(this.sentenceInputEditText.getText());

            viewModel.insertMotivation(new Motivation(sentence));
            return true;
        }
        return false;
    }

    private boolean validateMotivationForm() {
        if (sentenceInputEditText.getText().toString().isEmpty()) {
            sentenceInputLayout.setError(getResources().getString(R.string.task_title_required_error));
            sentenceInputLayout.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm_form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.confirm_form_item) {
            if (createMotivation()) {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}