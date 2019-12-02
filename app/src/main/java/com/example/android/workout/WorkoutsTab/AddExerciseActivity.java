package com.example.android.workout.WorkoutsTab;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.workout.Exercise;
import com.example.android.workout.ExerciseData.ExerciseContract;
import com.example.android.workout.ExerciseTab.ExerciseRecyclerViewAdapter;
import com.example.android.workout.MainActivity;
import com.example.android.workout.R;
import com.example.android.workout.WorkoutData.ExerciseDBHelper;

import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {

    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ExerciseDBHelper mDbHelper;
    private int date;

    // Return to previous activity when back button (bottom of screen) is pressed
    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddExerciseActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_exercise_activity);

        // Instantiate ExerciseDBHelper
        mDbHelper = new ExerciseDBHelper(this);

        // Receive date from workoutsFragment
        Intent intent = getIntent();
        date = intent.getIntExtra("date", 0);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.add_exercise_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddExerciseActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Create menu
        final ImageButton imageButton = findViewById(R.id.exercise_sort_button);
        final PopupMenu dropDownMenu = new PopupMenu(this, imageButton);
        final Menu menu = dropDownMenu.getMenu();
        menu.add(Menu.NONE, 0, 0, "All Exercises");
        menu.add(Menu.NONE, 2, 2,"By Category");
        menu.add(Menu.NONE, 3, 3,"By Most Recent");
        menu.add(Menu.NONE, 4, 4,"Favorites");

        // Set default to all exercisesFragment
        exercises.clear();
        exercises = getAllExercises();
        buildRecyclerView();

        // Show menu items if click on imageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.show();
            }
        });

        // Set up dropdown menu onClickListener
        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        exercises.clear();
                        exercises = getAllExercises(); break;
                    case 1:
                        exercises.clear();
                        break;
                    case 2:
                        exercises.clear();
                        break;
                    case 3:
                        exercises.clear();
                        break;
                }
                buildRecyclerView();
                return false;
            }
        });
    }

    // returns arrayList of all exercisesFragment
    private ArrayList<Exercise> getAllExercises() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM exercise", null);

        // Figure out the index of each column
        int exerciseColumnIndex = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NAME);
        int muscleColumnIndex = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MUSCLE);
        int equipmentColumnIndex = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT);
        int categoryColumnIndex = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_CATEGORY);
        int categoryNotesIndex = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NOTES);

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            String exercise = cursor.getString(exerciseColumnIndex);
            String muscle = cursor.getString(muscleColumnIndex);
            String equipment = cursor.getString(equipmentColumnIndex);
            String category = cursor.getString(categoryColumnIndex);
            String notes = cursor.getString(categoryNotesIndex);
            exercises.add(new Exercise(exercise, muscle, equipment, category, 0, 0, 0, notes));
        }
        cursor.close();

        return exercises;
    }

    private void buildRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.exercise_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ExerciseRecyclerViewAdapter adapter = new ExerciseRecyclerViewAdapter(exercises);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // When click on item, go to AddExerciseSetsActivity and send which exercise was clicked on
        adapter.setOnItemClickListener(new ExerciseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(AddExerciseActivity.this, AddExerciseSetsActivity.class);
                intent.putExtra("current_exercise", exercises.get(position));
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}