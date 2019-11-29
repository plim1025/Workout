package com.example.android.workout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android.workout.WorkoutData.WorkoutContract;
import com.example.android.workout.WorkoutData.WorkoutDBHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private ArrayList<Exercise> mExercises = new ArrayList<>();
    private int mLastPosition;

    // Constructor
    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    // This runs when new fragments need to be loaded into the adapter - loads 3 in at a time
    @NonNull
    @Override
    public Fragment getItem(int position) {

        Calendar CALENDAR = Calendar.getInstance();
        CALENDAR.add(Calendar.DATE, position);
        int years = CALENDAR.get(Calendar.YEAR);
        int months =  CALENDAR.get(Calendar.MONTH);
        int days = CALENDAR.get(Calendar.DAY_OF_MONTH);
        int dateInt = Integer.parseInt(Integer.toString(years) + Integer.toString(months) + Integer.toString(days));

        // Instantiate DBHelper
        WorkoutDBHelper mDbHelper = new WorkoutDBHelper(mContext);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = new String[] {WorkoutContract.WorkoutEntry.COLUMN_EXERCISE, WorkoutContract.WorkoutEntry.COLUMN_WEIGHT, WorkoutContract.WorkoutEntry.COLUMN_REPS};
        String[] selection = new String[] {Integer.toString(dateInt)};
        Cursor cursor = db.query(WorkoutContract.WorkoutEntry.TABLE_NAME, projection, "date = ?", selection, null, null, null);

        try {
            // Figure out the index of each column
            int exerciseColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_EXERCISE);
            int weightColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WEIGHT);
            int repsColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_REPS);

            // Read all exercisesFragment with same date as position and add to mExercises
            while(cursor.moveToNext()) {
                String currentExercise = cursor.getString(exerciseColumnIndex);
                float currentWeight = cursor.getFloat(weightColumnIndex);
                int currentReps = cursor.getInt(repsColumnIndex);
                Exercise newExercise = new Exercise(currentExercise, null, null, null, currentWeight, currentReps, 0, null);
                mExercises.add(newExercise);
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
        Bundle bundle = new Bundle();
        // Attach clone of mExercises to fragment
        bundle.putParcelableArrayList("attached_exercises", (ArrayList<? extends Parcelable>) mExercises.clone());
        // Clear exercisesFragment for further usage
        mExercises.clear();
        Fragment viewPagerFragment = new ViewPagerFragment();
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    // Returns number of fragments in adapter
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    // Returns page title for each fragment
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Calendar CALENDAR = Calendar.getInstance();
        CALENDAR.add(Calendar.DATE, position);
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(CALENDAR.getTime());
        return "          " + date.substring(0, date.length() - 6) + "          ";
    }
}

