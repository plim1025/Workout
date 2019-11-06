package com.example.android.workout;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DateFrag{

    private String mDate;
    private Fragment mFragment;
    private ArrayList<Exercise> mExercise;

    public DateFrag(String date, Fragment fragment, ArrayList<Exercise> exercise) {
        mDate = date;
        mFragment = fragment;
        mExercise = exercise;
    }

    public String getDate() { return mDate; }
    public Fragment getFragment() { return mFragment; }
    public ArrayList<Exercise> getExercise() { return mExercise; }
}

