package com.example.android.workout;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {

    float WEIGHTTEXT = 0;
    int REPSTEXT = 0;
    Exercise added_exercise;
    Bundle exercise_info = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.add_exercise_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Set up buttons and edit texts
        ImageButton minusWeight = findViewById(R.id.minus_weight);
        ImageButton addWeight = findViewById(R.id.add_weight);
        final EditText weightEditText = findViewById(R.id.weight_edit_text);
        ImageButton minusReps = findViewById(R.id.minus_reps);
        ImageButton addReps = findViewById(R.id.add_reps);
        final EditText repsEditText = findViewById(R.id.reps_edit_text);
        Button addSet = findViewById(R.id.add_set);
        Button clear_all = findViewById(R.id.clear);

        ListView add_exercise_list_view = findViewById(R.id.add_exercise_list_view);
        final ArrayList<Exercise> exercise = new ArrayList<Exercise>();

        // Receive exercise object when clicked on
        Intent intent = getIntent();
        final Exercise current_exercise = intent.getParcelableExtra("Exercises");

        // Initialize weight/reps to 0
        weightEditText.setText(String.valueOf(WEIGHTTEXT));
        repsEditText.setText(String.valueOf(REPSTEXT));

        minusWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WEIGHTTEXT >= 5) {
                    WEIGHTTEXT -= 5;
                    weightEditText.setText(String.valueOf(WEIGHTTEXT));
                }
            }
        });

        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WEIGHTTEXT += 5;
                weightEditText.setText(String.valueOf(WEIGHTTEXT));
            }
        });

        minusReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(REPSTEXT >= 1) {
                    REPSTEXT -= 1;
                    repsEditText.setText(String.valueOf(REPSTEXT));
                }
            }
        });

        addReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REPSTEXT += 1;
                repsEditText.setText(String.valueOf(REPSTEXT));
            }
        });

        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                added_exercise = new Exercise(current_exercise.getName(), current_exercise.getMuscleGroup(), current_exercise.getType(), current_exercise.getEquipment(), WEIGHTTEXT, REPSTEXT);
                exercise.add(added_exercise);
            }
        });

        clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise.remove(added_exercise);
            }
        });

        ExerciseAdapter adapter = new ExerciseAdapter(this, exercise);
        add_exercise_list_view.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // send ArrayList of exercises to WorkoutsFragment
    }
}
