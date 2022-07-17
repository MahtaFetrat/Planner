package com.example.planner.ui.views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planner.R;
import com.example.planner.model.Motivation;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MotivationAdapter extends RecyclerView.Adapter<MotivationAdapter.MotivationViewHolder> {
    private final Context context;
    private List<Motivation> motivations;

    public MotivationAdapter(Context ct, List<Motivation> mtv) {
        context = ct;
        motivations = mtv;
    }

    @NonNull
    @Override
    public MotivationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.motivation_row, parent, false);
        return new MotivationAdapter.MotivationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationViewHolder holder, int position) {
        holder.motivationText.setText(motivations.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return motivations.size();
    }

    public void updateList(List<Motivation> allMotivations) {
        motivations = allMotivations;
        notifyDataSetChanged();
    }

    public class MotivationViewHolder extends RecyclerView.ViewHolder {
        TextView motivationText;

        public MotivationViewHolder(@NonNull View itemView) {
            super(itemView);

            motivationText = itemView.findViewById(R.id.motivationTextView);
        }
    }
}
