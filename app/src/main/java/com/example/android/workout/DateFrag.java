package com.example.android.workout;

import java.util.ArrayList;

public class DateFrag{

    private int[] mDate;
    private ArrayList<Exercise> mExercise;

    public DateFrag(int[] date, ArrayList<Exercise> exercise) {
        mDate = date;
        mExercise = exercise;
    }

    public int[] getDate() { return mDate; }
    public ArrayList<Exercise> getExercise() { return mExercise; }
}

