package com.example.android.workout;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private ArrayList<DateFrag> mDateFrags;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context, ArrayList<DateFrag> dateFrags) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mDateFrags = dateFrags;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        for(int i = 0; i < mDateFrags.size(); i++) {
            Calendar CALENDAR = Calendar.getInstance();
            CALENDAR.add(Calendar.DATE, position);
            int Today = CALENDAR.getTime().getDay();
            int fragDate = mDateFrags.get(i).getDate();
            if(Today == fragDate) {
                Fragment fragment = mDateFrags.get(i).getFragment();
                Bundle bundle = new Bundle();
                ArrayList<Exercise> exercises = mDateFrags.get(0).getExercise();
                bundle.putParcelableArrayList("attached_exercises", exercises);
                fragment.setArguments(bundle);
                return fragment;
            }
        }
        return new ViewPagerFragment();
    }

    @Override
    public int getCount() {
        return 365;
    }


}
/*
for(int i = 0; i < mDateFrags.size(); i++) {
        Calendar CALENDAR = Calendar.getInstance();
        CALENDAR.add(Calendar.DATE, position);
        Date fragDate = mDateFrags.get(i).getDate().getTime();
        if(CALENDAR.getTime() == fragDate) {
        Fragment fragment = mDateFrags.get(i).getFragment();
        Bundle bundle = new Bundle();
        ArrayList<Exercise> exercises = mDateFrags.get(0).getExercise();
        bundle.putParcelableArrayList("attached_exercises", exercises);
        fragment.setArguments(bundle);
        return fragment;
        }
        }

 */
