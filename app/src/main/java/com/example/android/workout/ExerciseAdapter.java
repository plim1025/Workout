package com.example.android.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExerciseAdapter extends ArrayAdapter {

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

        Exercise currentExercise = (Exercise) getItem(position);


        TextView exercise_name = convertView.findViewById(R.id.exercise_item);
        exercise_name.setText(currentExercise.getName());

        return convertView;
    }
}
