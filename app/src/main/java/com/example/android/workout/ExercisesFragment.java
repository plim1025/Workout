package com.example.android.workout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.workout.ExerciseData.ExerciseContract;
import com.example.android.workout.WorkoutData.ExerciseDBHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExercisesFragment extends Fragment {

    private View view;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ExerciseDBHelper mDbHelper;

    @Override
    public void onResume() {
        exercises.clear();
        exercises = getAllExercises();
        buildRecyclerView();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.exercisesFragment, container, false);

        // Instantiate ExerciseDBHelper
        mDbHelper = new ExerciseDBHelper(this.getActivity());

        // Hide keyboard when enter activity
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Create FAB
        FloatingActionButton fab1 = view.findViewById(R.id.exercises_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateNewExerciseActivity.class));
            }
        });

        // Create menu
        final ImageButton imageButton = view.findViewById(R.id.exercise_sort_button);
        final PopupMenu dropDownMenu = new PopupMenu(getContext(), imageButton);
        final Menu menu = dropDownMenu.getMenu();
        menu.add(Menu.NONE, 0, 0, "Basic List");
        menu.add(Menu.NONE, 1, 1, "Complex List");
        menu.add(Menu.NONE, 2, 2, "By Category");
        menu.add(Menu.NONE, 3, 3, "By Most Recent");
        menu.add(Menu.NONE, 4, 4, "Favorites");

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public String toString() {
        return ExercisesFragment.class.getSimpleName();
    }

    // returns arrayList of all exercisesFragment
    private ArrayList<Exercise> getAllExercises() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM exercises", null);

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
        RecyclerView recyclerView = view.findViewById(R.id.exercise_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ExerciseRecyclerViewAdapter adapter = new ExerciseRecyclerViewAdapter(exercises);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ExerciseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), AddExerciseSetsActivity.class);
                intent.putExtra("Exercises", exercises.get(position));
                startActivity(intent);
            }
        });
    }
}