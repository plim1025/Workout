package com.example.android.workout.WorkoutData;

import android.provider.BaseColumns;


public class WorkoutContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private WorkoutContract() {}


    public static abstract class WorkoutEntry implements BaseColumns {

        // Name of database table
        public static final String TABLE_NAME = "workouts";

        // Type: INTEGER
        public static final String COLUMN_DATE = "date";
        // Type: TEXT
        public static final String COLUMN_EXERCISE = "exercise";
        // Type: INTEGER
        public static final String COLUMN_WEIGHT = "weight";
        // Type: INTEGER
        public static final String COLUMN_REPS = "reps";
    }
}
