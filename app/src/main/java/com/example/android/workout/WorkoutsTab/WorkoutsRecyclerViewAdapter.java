package com.example.android.workout.WorkoutsTab;

import android.content.Context;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.workout.Exercise;
import com.example.android.workout.R;

import java.util.ArrayList;

public class WorkoutsRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<Exercise> mExercises;
    private OnLongItemClickListener mOnLongClickListener;
    private OnClickListener mOnClickListener;

    public WorkoutsRecyclerViewAdapter(ArrayList<Exercise> exercises) {
        mExercises = exercises;
    }

    public interface OnLongItemClickListener {
        void onItemLongClick(int position);
    }

    public void setOnLongClickListener(OnLongItemClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView exercise_name;
        private TextView exercise_weight;
        private TextView exercise_reps;

        private RecyclerViewHolder(@NonNull final View itemView, final OnLongItemClickListener onLongClickListener, final OnClickListener onClickListener) {
            super(itemView);
            exercise_name = itemView.findViewById(R.id.workout_exercise_name);
            exercise_weight = itemView.findViewById(R.id.workout_exercise_weight);
            exercise_reps = itemView.findViewById(R.id.workout_exercise_reps);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onLongClickListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            onLongClickListener.onItemLongClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            onClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_exercise_item, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v, mOnLongClickListener, mOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Exercise currentItem = mExercises.get(position);
        String current_exercise_name = currentItem.getName();
        float current_exercise_weight = currentItem.getWeight();
        int current_exercise_reps = currentItem.getReps();
        if(current_exercise_name.length() > 40) {
            current_exercise_name = current_exercise_name.substring(0, Math.min(current_exercise_name.length(), 40)) + "...";
        }
        holder.exercise_name.setText(current_exercise_name);
        holder.exercise_weight.setText(String.valueOf(current_exercise_weight) + " lbs");
        holder.exercise_reps.setText(String.valueOf(current_exercise_reps) + " reps");
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }
}
