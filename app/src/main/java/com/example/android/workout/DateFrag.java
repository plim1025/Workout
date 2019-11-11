package com.example.android.workout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

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

