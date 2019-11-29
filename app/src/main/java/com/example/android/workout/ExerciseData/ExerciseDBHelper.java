package com.example.android.workout.WorkoutData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.workout.Exercise;
import com.example.android.workout.ExerciseData.ExerciseContract;

public class ExerciseDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ExerciseDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "exercise.db";
    private static final int DATABASE_VERSION = 1;

    public ExerciseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the workout table
        String SQL_CREATE_EXERCISE_TABLE = "CREATE TABLE " + ExerciseContract.ExerciseEntry.TABLE_NAME + " ("
                + ExerciseContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExerciseContract.ExerciseEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ExerciseContract.ExerciseEntry.COLUMN_MUSCLE + " TEXT NOT NULL, "
                + ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT + " TEXT, "
                + ExerciseContract.ExerciseEntry.COLUMN_CATEGORY + " TEXT NOT NULL,"
                + ExerciseContract.ExerciseEntry.COLUMN_NOTES + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EXERCISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Only 1 version for now, leave empty
    }
}
