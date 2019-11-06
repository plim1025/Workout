package com.example.android.workout;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<DateFrag> mDateFrags = new ArrayList<>();
    private Calendar CALENDAR;
    private int DATE = Integer.parseInt(DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime()));

    // This holds all the currently displayable views, in order from left to right.
    private ArrayList<View> views = new ArrayList<View>();

    public ViewPagerAdapter(FragmentManager fm, Context context, ArrayList<DateFrag> dateFrags) {
        super(fm);
        mContext = context;
        mDateFrags = dateFrags;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        for(int i = 0; i < mDateFrags.size(); i++) {
            if(position == Integer.parseInt(mDateFrags.get(i).getDate()) - DATE) {
                Fragment fragment = mDateFrags.get(i).getFragment();
                return fragment;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mDateFrags.size();
    }
}

