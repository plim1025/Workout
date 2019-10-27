package com.example.android.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<Exercise> mExercises;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView weightTextView;
        public TextView repTextView;
        public TextView setTextView;

        public RecyclerViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            weightTextView = itemView.findViewById(R.id.exercise_weight);
            repTextView = itemView.findViewById(R.id.exercise_reps);
            setTextView = itemView.findViewById(R.id.exercise_set);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public WorkoutRecyclerViewAdapter(ArrayList<Exercise> exercises) {
        mExercises = exercises;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_exercise_item, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Exercise currentItem = mExercises.get(position);
        holder.weightTextView.setText(String.valueOf(currentItem.getWeight()) + " lbs");
        holder.repTextView.setText(String.valueOf(currentItem.getReps()) + " reps");
        holder.setTextView.setText("Set " + String.valueOf(currentItem.getSet()));
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }
}
