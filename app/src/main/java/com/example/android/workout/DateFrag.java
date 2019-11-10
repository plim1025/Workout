package com.example.android.workout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class DateFrag{

    private int mDate;
    private Fragment mFragment;
    private ArrayList<Exercise> mExercise;

    public DateFrag(int date, Fragment fragment, ArrayList<Exercise> exercise) {
        mDate = date;
        mFragment = fragment;
        mExercise = exercise;
    }

    public int getDate() { return mDate; }
    public Fragment getFragment() { return mFragment; }
    public ArrayList<Exercise> getExercise() { return mExercise; }
}

