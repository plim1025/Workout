package com.example.android.workout.ExerciseData;

import android.provider.BaseColumns;

public class ExerciseContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ExerciseContract() {}


    public static abstract class ExerciseEntry implements BaseColumns {

        // Name of database table
        public static final String TABLE_NAME = "exercises";

        // Type: TEXT
        public static final String COLUMN_NAME = "name";
        // Type: INTEGER
        public static final String COLUMN_MUSCLE = "muscle";
        // Type: INTEGER
        public static final String COLUMN_TYPE = "type";
        // Type: TEXT
        public static final String COLUMN_EQUIPMENT = "equipment";
        // Type: INTEGER
        public static final String COLUMN_CATEGORY = "category";

        public static final int MUSCLE_ABS = 0;
        public static final int MUSCLE_BACK = 1;
        public static final int MUSCLE_BICEPS = 2;
        public static final int MUSCLE_CARDIO = 3;
        public static final int MUSCLE_CHEST = 4;
        public static final int MUSCLE_GLUTES = 5;
        public static final int MUSCLE_LOWER_LEG = 6;
        public static final int MUSCLE_TRICEPS = 7;
        public static final int MUSCLE_UPPER_LEG = 8;

        public static final int CATEGORY_WEIGHT_REPS = 0;
        public static final int CATEGORY_WEIGHT_DISTANCE = 1;
        public static final int CATEGORY_WEIGHT_TIME = 2;
        public static final int CATEGORY_REPS_DISTANCE = 3;
        public static final int CATEGORY_REPS_TIME = 4;
        public static final int CATEGORY_DISTANCE_TIME = 5;
        public static final int CATEGORY_WEIGHT = 6;
        public static final int CATEGORY_REPS = 7;
        public static final int CATEGORY_DISTANCE = 8;
        public static final int CATEGORY_TIME = 9;
    }
}
