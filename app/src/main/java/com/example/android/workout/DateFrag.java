package com.example.android.workout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class DateFrag{

    private Calendar mDate;
    private Fragment mFragment;
    private ArrayList<Exercise> mExercise;

    public DateFrag(Calendar date, Fragment fragment, ArrayList<Exercise> exercise) {
        mDate = date;
        mFragment = fragment;
        mExercise = exercise;
    }

    public Calendar getDate() { return mDate; }
    public Fragment getFragment() { return mFragment; }
    public ArrayList<Exercise> getExercise() { return mExercise; }
}

