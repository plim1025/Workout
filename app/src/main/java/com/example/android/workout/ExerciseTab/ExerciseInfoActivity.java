package com.example.android.workout.ExerciseTab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.workout.Exercise;
import com.example.android.workout.R;

import org.w3c.dom.Text;

public class ExerciseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.exercise_activity_info);

        // Receive exercise object from ExercisesFragment
        Intent intent = getIntent();
        final Exercise current_exercise = intent.getParcelableExtra("exercise");

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.exercise_activity_info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(current_exercise.getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set up textViews
        TextView exercise_muscle = findViewById(R.id.exercise_activity_muscle);
        TextView exercise_equipment = findViewById(R.id.exercise_activity_equipment);
        TextView exercise_notes = findViewById(R.id.exercise_activity_notes);
        CheckBox exercise_favoritable = findViewById(R.id.exercise_activity_favoritable);

        // Set textView texts
        exercise_muscle.setText("Muscles Worked: " + current_exercise.getMuscleGroup());
        exercise_equipment.setText("Equipment Needed: " + current_exercise.getEquipment());
        exercise_notes.setText("Additional Notes: " + current_exercise.getNotes());

        boolean favorite = exercise_favoritable.isChecked();
    }
}
