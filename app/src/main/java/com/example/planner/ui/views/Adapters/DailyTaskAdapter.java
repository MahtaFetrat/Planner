package com.example.planner.ui.views.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planner.R;
import com.example.planner.model.DailyTask;
import com.example.planner.ui.viewModels.TaskViewModel;

import java.util.List;

public class DailyTaskAdapter extends RecyclerView.Adapter<DailyTaskAdapter.DailyTaskViewHolder> {
    private final Context context;
    private List<DailyTask> dailyTasks;
    private TaskViewModel viewModel;


    public DailyTaskAdapter(Context ct, List<DailyTask> dailyTsk, TaskViewModel vm) {
        dailyTasks = dailyTsk;
        context = ct;
        viewModel = vm;
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
        holder.dailyTaskIsDone.setChecked(dailyTasks.get(position).isDone());
        if (dailyTasks.get(position).isDone()) {
            holder.dailyTaskName.setPaintFlags(holder.dailyTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.dailyTaskName.setPaintFlags(0);
        }

        holder.dailyTaskIsDone.setOnCheckedChangeListener((compoundButton, b) -> {
            dailyTasks.get(position).setDone(b);
            viewModel.updateDailyTask(dailyTasks.get(position));
            if (b) {
                holder.dailyTaskName.setPaintFlags(holder.dailyTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.dailyTaskName.setPaintFlags(0);
            }
        });
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
