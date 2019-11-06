package com.example.android.workout;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String mDate;
    private ArrayList<Exercise> mExercises;

    // This holds all the currently displayable views, in order from left to right.
    private ArrayList<View> views = new ArrayList<View>();

    public ViewPagerAdapter(Context context, String date, ArrayList<Exercise> exercises) {
        mContext = context;
        mDate = date;
        mExercises = exercises;
    }
}

