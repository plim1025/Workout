package com.example.android.workout;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.YEAR;

class DataHolder {
    private Calendar CALENDAR = Calendar.getInstance();
    final ArrayList<Exercise> exercises = new ArrayList<>();
    final ArrayList<DateFrag> dateFrags = new ArrayList<>();
    final int[] date = {CALENDAR.get(Calendar.DAY_OF_MONTH), CALENDAR.get(Calendar.MONTH), CALENDAR.get(YEAR)};

    private DataHolder() {}

    static DataHolder getInstance() {
        if( instance == null ) {
            instance = new DataHolder();
        }
        return instance;
    }

    private static DataHolder instance;
}
