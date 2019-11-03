package com.example.android.workout;

import java.util.ArrayList;

class DataHolder {
    final ArrayList<Exercise> exercises = new ArrayList<>();

    private DataHolder() {}

    static DataHolder getInstance() {
        if( instance == null ) {
            instance = new DataHolder();
        }
        return instance;
    }

    private static DataHolder instance;
}
