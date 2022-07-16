package com.example.planner.ui.views.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planner.R;
import com.example.planner.model.PriorityLevel;
import com.example.planner.model.Task;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private final Context context;
    private List<Task> tasks;


    public TasksAdapter(Context ct, List<Task> tsk) {
        tasks = tsk;
        context = ct;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.taskName.setText(tasks.get(position).getTitle());
        PriorityLevel priority = tasks.get(position).getPriorityLevel();
        int priorityColor = (priority == PriorityLevel.REGULAR ? R.color.regular : (priority == PriorityLevel.IMPORTANT ? R.color.important : R.color.essential));
        holder.taskPriorityColor.setBackgroundColor(ContextCompat.getColor(context, priorityColor));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void updateList(List<Task> allTasks) {
        tasks = allTasks;
        notifyDataSetChanged();
    }

    public static class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView taskName;
        View taskPriorityColor;
        MaterialCardView taskLayout;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskPriorityColor = itemView.findViewById(R.id.taskPriorityColor);
            taskLayout = itemView.findViewById(R.id.taskRowLayout);
        }
    }
}
