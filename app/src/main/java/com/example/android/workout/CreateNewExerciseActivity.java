package com.example.android.workout;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.workout.ExerciseData.ExerciseContract;
import com.example.android.workout.WorkoutData.ExerciseDBHelper;
import com.example.android.workout.WorkoutData.WorkoutContract;
import com.example.android.workout.WorkoutData.WorkoutDBHelper;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class CreateNewExerciseActivity extends AppCompatActivity{

    private ExerciseDBHelper mDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_exercise);

        // Set up textViews
        final TextView exercise_name = findViewById(R.id.create_new_exercise_name);
        final TextView exercise_equipment = findViewById(R.id.create_new_exercise_equipment);
        final TextView exercise_notes = findViewById(R.id.create_new_exercise_notes);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.create_new_exercise_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Set up muscle spinner
        String[] muscleSpinnerArray = new String[] {"", "Abs", "Back", "Biceps", "Cardio", "Chest", "Glutes", "Lower Legs", "Triceps", "Upper Legs"};
        final Spinner muscleSpinner = findViewById(R.id.create_new_exercise_muscle);
        ArrayAdapter<String> muscleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, muscleSpinnerArray);
        muscleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleSpinner.setAdapter(muscleAdapter);

        // Set up category spinner
        String[] categorySpinnerArray = new String[] { "", "Weight and Reps", "Weight and Distance", "Weight and Time", "Reps and Distance", "Reps and Time", "Distance and Time", "Weight", "Reps", "Distance", "Time"};
        final Spinner categorySpinner = findViewById(R.id.create_new_exercise_category);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categorySpinnerArray);
        categorySpinner.setAdapter(categoryAdapter);

        // Set up FAB
        Button button_cancel = findViewById(R.id.create_new_exercise_button_cancel);
        Button button_ok = findViewById(R.id.create_new_exercise_button_ok);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = exercise_name.getText().toString().trim();
                String muscle = muscleSpinner.getSelectedItem().toString().trim();
                String equipment = exercise_equipment.getText().toString().trim();
                String category = categorySpinner.getSelectedItem().toString().trim();
                String notes = exercise_notes.getText().toString().trim();
                insertExercises(name, muscle, equipment, category, notes);
            }
        });

    }
    // Creates top navigation menu
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_top_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Insert exercise to exercise database
    public void insertExercises(String name, String muscle, String equipment, String category, String notes) {

        mDbHelper = new ExerciseDBHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        ContentValues values = new ContentValues();
        values.put(ExerciseContract.ExerciseEntry.COLUMN_NAME.trim(), name);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_MUSCLE.trim(), muscle);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT.trim(), equipment);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_CATEGORY.trim(), category);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_NOTES.trim(), notes);

        // function returns _id, and if error occurs _id is set to -1
        if(name == "" || muscle == "" || category == "") {
            Toast toast = Toast.makeText(getApplicationContext(), "The following categories are required", Toast. LENGTH_SHORT);
            toast.show();
        } else {
            long newRowId = db.insert(ExerciseContract.ExerciseEntry.TABLE_NAME, null, values);
            finish();
        }
    }
}
