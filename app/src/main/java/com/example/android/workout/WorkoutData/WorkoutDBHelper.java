package com.example.android.workout.WorkoutData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.workout.WorkoutData.WorkoutContract.WorkoutEntry;

public class WorkoutDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = WorkoutDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "workout.db";
    private static final int DATABASE_VERSION = 1;

    public WorkoutDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the workout table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " ("
                + WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WorkoutEntry.COLUMN_DATE + " INTEGER NOT NULL, "
                + WorkoutEntry.COLUMN_EXERCISE + " INTEGER NOT NULL, "
                + WorkoutEntry.COLUMN_WEIGHT + " REAL, "
                + WorkoutEntry.COLUMN_REPS + " INTEGER NOT NULL);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Only 1 version for now, leave empty
    }
}
