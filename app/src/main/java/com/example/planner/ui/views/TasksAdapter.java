package com.example.planner.ui.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planner.R;
import com.example.planner.model.Task;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private Context context;
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
        holder.taskDescription.setText(tasks.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void updateList(List<Task> allTasks) {
        tasks = allTasks;
        notifyDataSetChanged();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView taskName;
        TextView taskDescription;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskDescription = itemView.findViewById(R.id.taskDescriptionTextView);
        }
    }
}
