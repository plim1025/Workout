package com.example.android.workout;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.android.workout.ExerciseData.ExerciseContract;
import com.example.android.workout.ExerciseTab.ExercisesFragment;
import com.example.android.workout.WorkoutData.ExerciseDBHelper;
import com.example.android.workout.WorkoutsTab.WorkoutsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String ACTIVITY_NAME = MainActivity.class.getSimpleName();
    private static final String TAG = ACTIVITY_NAME;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fragment;
    private Bundle bundle = new Bundle();
    private ExerciseDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        // Check if exercise database is empty
        mDbHelper = new ExerciseDBHelper(this);
        boolean dbExists = true;
        SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM exercise", null);
        dbExists = cursor.moveToNext();
        cursor.close();

        // If exercise database empty, add list from JSON file
        if(!dbExists) {
            try {
                JSONObject JSON = new JSONObject(readJSONFromAsset());
                JSONObject json = JSON.getJSONObject("exercise_info");
                JSONArray exercise_names = json.names();
                for(int i = 0; i < exercise_names.length(); i++){
                    JSONObject current_exercise_info = json.getJSONObject(exercise_names.getString(i));
                    insertExercise(exercise_names.getString(i), current_exercise_info.getString("Main Muscle Group"), current_exercise_info.getString("Equipment"), current_exercise_info.getString("Category"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creates bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();

        // Put today's date as viewPager Position
        SharedPreferences.Editor editor = getSharedPreferences("viewPager position", 0).edit();
        editor.putInt("viewPager position", 0);
        editor.apply();

        // Set home fragment to workout tab
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        Fragment initial_fragment = new WorkoutsFragment();
        initial_fragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, initial_fragment, "0");
        fragmentTransaction.addToBackStack("Add " + initial_fragment.toString());
        fragmentTransaction.commit();

        // Set up listeners for bottom nav
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_workout:
                        fragment = new WorkoutsFragment();
                        break;
                    case R.id.nav_exercises:
                        fragment = new ExercisesFragment();
                        break;
                    case R.id.nav_progress:
                        fragment = new ProgressFragment();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, "0");

                // Need to title it so that fragment tier 2 can return to tier 1 after pressing android back button
                fragmentTransaction.addToBackStack("Add main");
                fragmentTransaction.commit();

                return true;
            }
        });
    }

    // Set up listeners for top right menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_calendar:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.dropdown_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.dropdown_themes:
                startActivity(new Intent(this, ThemesActivity.class));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    // Creates top navigation menu
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Set up fragment/activity hierarchy
    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        // If press back button on fragment tier 1, return to home and pause app
        if (fragment == fragmentManager.findFragmentByTag("0")) {
            fragmentManager.popBackStack(0,0);
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);

            //  If press back button on fragment tier 2, replace frame with fragment tier 2 by searching with tag 0 and pop backstack up until last fragment tier 1
        } else if (fragment == fragmentManager.findFragmentByTag("1")) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragmentManager.findFragmentByTag("0"), "0");
            fragmentTransaction.addToBackStack("Add " + fragment.toString());
            fragmentTransaction.commit();
            fragmentManager.popBackStack("Add main", 0);
        }
    }

    // Read basic exercisesFragment JSON file
    private String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("exercise_info_basic");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Insert exercise to exercise database
    public void insertExercise(String name, String muscle, String equipment, String category) {

        mDbHelper = new ExerciseDBHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        ContentValues values = new ContentValues();
        values.put(ExerciseContract.ExerciseEntry.COLUMN_NAME, name);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_MUSCLE, muscle);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT, equipment);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_CATEGORY, category);

        // function returns _id, and if error occurs _id is set to -1
        long newRowId = db.insert(ExerciseContract.ExerciseEntry.TABLE_NAME, null, values);
    }
}