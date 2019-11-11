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

import static java.util.Calendar.YEAR;

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
        Calendar CALENDAR = Calendar.getInstance();
        CALENDAR.add(Calendar.DATE, position);
        int[] date = {CALENDAR.get(Calendar.DAY_OF_MONTH), CALENDAR.get(Calendar.MONTH), CALENDAR.get(YEAR)};

        for(int i = 0; i < mDateFrags.size(); i++) {
            if((mDateFrags.get(i).getDate()[0] == date[0]) && (mDateFrags.get(i).getDate()[1] == date[1]) && (mDateFrags.get(i).getDate()[2] == date[2])) {
                Bundle bundle = new Bundle();
                Fragment fragment = new ViewPagerFragment();
                ArrayList<Exercise> exercises = mDateFrags.get(i).getExercise();
                bundle.putParcelableArrayList("attached_exercises", exercises);
                fragment.setArguments(bundle);
                return fragment;
            }
        }
        return new ViewPagerFragment();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 3;
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
