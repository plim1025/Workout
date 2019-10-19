package com.example.android.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ExerciseAdapter extends ArrayAdapter {

    public Exercise currentExercise;

    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.exercise_list_item, parent, false);
        }

        currentExercise = (Exercise) getItem(position);

        TextView exercise_name = convertView.findViewById(R.id.exercise_item);
        String current_name = currentExercise.getName();
        if (current_name.length() > 48) {
            current_name = current_name.substring(0, Math.min(current_name.length(), 45)) + "...";
        }
        exercise_name.setText(current_name);

        return convertView;
    }
}
