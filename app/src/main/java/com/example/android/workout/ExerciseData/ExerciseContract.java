package com.example.android.workout.ExerciseData;

import android.provider.BaseColumns;

public class ExerciseContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ExerciseContract() {}

    public static abstract class ExerciseEntry implements BaseColumns {

        // Name of database table
        public static final String TABLE_NAME = "exercise";

        // Type: TEXT
        public static final String COLUMN_NAME = "name";
        // Type: TEXT
        public static final String COLUMN_MUSCLE = "muscle";
        // Type: TEXT
        public static final String COLUMN_EQUIPMENT = "equipment";
        // Type: TEXT
        public static final String COLUMN_CATEGORY = "category";
        // Type: TEXT
        public static final String COLUMN_NOTES = "notes";
    }
}
