package com.example.android.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {

    private ArrayList<Exercise> exercise = new ArrayList<Exercise>();
    private RecyclerView mRecyclerView;
    private WorkoutRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    float WEIGHTTEXT = 0;
    int REPSTEXT = 0;
    Exercise added_exercise;
    int exercise_items = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);

        // Hide keyboard when open activity
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        final ImageButton minusWeight = findViewById(R.id.minus_weight);
        ImageButton addWeight = findViewById(R.id.add_weight);
        final EditText weightEditText = findViewById(R.id.weight_edit_text);
        ImageButton minusReps = findViewById(R.id.minus_reps);
        ImageButton addReps = findViewById(R.id.add_reps);
        final EditText repsEditText = findViewById(R.id.reps_edit_text);
        Button addSet = findViewById(R.id.add_set);
        Button clear_all = findViewById(R.id.clear);

        // Receive exercise object when clicked on
        Intent intent = getIntent();
        final Exercise current_exercise = intent.getParcelableExtra("Exercises");

        // Initialize weight/reps to 0
        weightEditText.setText(String.valueOf(WEIGHTTEXT));
        repsEditText.setText(String.valueOf(REPSTEXT));

        buildRecyclerView();

        // Set checkmark bottom right of keyboards when click on editTexts
        weightEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        repsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        minusWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WEIGHTTEXT >= 5) {
                    WEIGHTTEXT = Float.valueOf(weightEditText.getText().toString());
                    WEIGHTTEXT -= 5;
                    weightEditText.setText(String.valueOf(WEIGHTTEXT));
                }
            }
        });

        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WEIGHTTEXT = Float.valueOf(weightEditText.getText().toString());
                WEIGHTTEXT += 5;
                weightEditText.setText(String.valueOf(WEIGHTTEXT));
            }
        });

        minusReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(REPSTEXT >= 1) {
                    REPSTEXT = Integer.parseInt(repsEditText.getText().toString());
                    REPSTEXT -= 1;
                    repsEditText.setText(String.valueOf(REPSTEXT));
                }
            }
        });

        addReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REPSTEXT = Integer.parseInt(repsEditText.getText().toString());
                REPSTEXT += 1;
                repsEditText.setText(String.valueOf(REPSTEXT));
            }
        });

        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WEIGHTTEXT = Float.valueOf(weightEditText.getText().toString());
                REPSTEXT = Integer.parseInt(repsEditText.getText().toString());
                added_exercise = new Exercise(current_exercise.getName(), current_exercise.getMuscleGroup(), current_exercise.getType(), current_exercise.getEquipment(), WEIGHTTEXT, REPSTEXT, exercise_items+1);
                insertItem(exercise_items, added_exercise);
                exercise_items++;
            }
        });

        clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise.clear();
                mAdapter.notifyItemRangeRemoved(0, exercise_items);
                exercise_items = 0;
            }
        });


    }

    public void insertItem(int position, Exercise added_exercise) {
        exercise.add(position, added_exercise);
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        exercise.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.add_exercise_recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new WorkoutRecyclerViewAdapter(exercise);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        // send ArrayList of exercises to WorkoutsFragment
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("exercise", exercise);
        startActivity(i);
        // clear exercise ArrayList

        super.onPause();
    }
}
