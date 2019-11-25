package com.example.android.workout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.workout.WorkoutData.WorkoutContract;
import com.example.android.workout.WorkoutData.WorkoutDBHelper;

import java.util.ArrayList;
import java.util.Collection;

public class AddExerciseSetsActivity extends AppCompatActivity {

    // stores exercises in recyclerView
    private ArrayList<Exercise> deletable_exercises = new ArrayList<>();

    // exercise clicked on in addExerciseActivity
    private Exercise added_exercise;
    private int exercise_items = 0;

    private WorkoutDBHelper mDbHelper;
    private RecyclerView mRecyclerView;
    private AddExerciseRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private float WEIGHTTEXT = 0;
    private int REPSTEXT = 0;


    // Return to previous activity when back button (bottom) is pressed
    @Override
    public void onBackPressed() {
        finish();
    }

    // Return to previous activity when back button (top) is pressed
    @Override
    protected void onPause() {
        Intent i = new Intent(this, AddExerciseActivity.class);
        // Clear recyclerView
        deletable_exercises.clear();
        insertExercisestoDb();
        startActivity(i);
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_sets);

        // Hide keyboard when open activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Receive exercise object when clicked on
        Intent intent = getIntent();
        final Exercise current_exercise = intent.getParcelableExtra("current_exercise");

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.add_exercise_sets_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(current_exercise.getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        // Initialize weight/reps to 0
        weightEditText.setText(String.valueOf(WEIGHTTEXT));
        repsEditText.setText(String.valueOf(REPSTEXT));

        buildRecyclerView();

        // Set checkmark bottom right of keyboards when click on editTexts
        weightEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        repsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        // Set up + and - buttons
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

        // When click add set, add to recyclerView
        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WEIGHTTEXT = Float.valueOf(weightEditText.getText().toString());
                REPSTEXT = Integer.parseInt(repsEditText.getText().toString());
                added_exercise = new Exercise(current_exercise.getName(), current_exercise.getMuscleGroup(), current_exercise.getType(), current_exercise.getEquipment(), WEIGHTTEXT, REPSTEXT, exercise_items+1);
                deletable_exercises.add(exercise_items, added_exercise);
                mAdapter.notifyItemInserted(exercise_items);
                exercise_items++;
            }
        });

        // When click clear all, clear all from recyclerView
        clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletable_exercises.clear();
                mAdapter.notifyItemRangeRemoved(0, exercise_items);
                exercise_items = 0;
            }
        });
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.add_exercise_sets_recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new AddExerciseRecyclerViewAdapter(deletable_exercises);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void insertExercisestoDb() {

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.WorkoutEntry.COL
        values.put(WorkoutContract.WorkoutEntry.COLUMN_EXERCISE, added_exercise.getName());
        values.put(WorkoutContract.WorkoutEntry.COLUMN_WEIGHT, WEIGHTTEXT);
        values.put(WorkoutContract.WorkoutEntry.COLUMN_REPS, REPSTEXT);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, values);
    }
}