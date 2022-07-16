package com.example.planner.ui.views;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planner.R;
import com.example.planner.model.DailyTask;

import java.util.List;

public class DailyTaskAdapter extends RecyclerView.Adapter<DailyTaskAdapter.DailyTaskViewHolder> {
    private final Context context;
    private List<DailyTask> dailyTasks;


    public DailyTaskAdapter(Context ct, List<DailyTask> dailyTsk) {
        dailyTasks = dailyTsk;
        context = ct;
    }

    @NonNull
    @Override
    public DailyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.daily_task_row, parent, false);
        return new DailyTaskAdapter.DailyTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyTaskViewHolder holder, int position) {
        holder.dailyTaskName.setText(dailyTasks.get(position).getTitle());
        if (dailyTasks.get(position).isDone()) {
            holder.dailyTaskIsDone.setChecked(true);
        }
        if (holder.dailyTaskIsDone.isChecked()) {
            holder.dailyTaskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.dailyTaskName.setPaintFlags(0);
        }
        holder.dailyTaskIsDone.setOnClickListener(view -> dailyTasks.get(position).setDone(holder.dailyTaskIsDone.isChecked()));
    }

    @Override
    public int getItemCount() {
        return dailyTasks.size();
    }

    public void updateList(List<DailyTask> allDailyTasks) {
        dailyTasks = allDailyTasks;
        notifyDataSetChanged();
    }

    public static class DailyTaskViewHolder extends RecyclerView.ViewHolder {

        TextView dailyTaskName;
        CheckBox dailyTaskIsDone;

        public DailyTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            dailyTaskName = itemView.findViewById(R.id.dailyTaskTextView);
            dailyTaskIsDone = itemView.findViewById(R.id.dailyTaskCheckBox);
        }
    }
}
