package com.example.android.workout;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.YEAR;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private ArrayList<DateFrag> mDateFrags = DataHolder.getInstance().dateFrags;
    private ArrayList<Exercise> mExercises;
    private int[] mItemDate;

    // Constructor
    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context, int[] itemDate, ArrayList<Exercise> exercises) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mItemDate = itemDate;
        mExercises = exercises;
    }

    // This runs when new fragments need to be loaded into the adapter - loads 3 in at a time
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Calendar CALENDAR = Calendar.getInstance();
        CALENDAR.add(Calendar.DATE, position);
        Bundle bundle = new Bundle();
        int date[] = {CALENDAR.get(Calendar.DAY_OF_MONTH), CALENDAR.get(Calendar.MONTH), CALENDAR.get(YEAR)};
        for(int i = 0; i < mDateFrags.size(); i++) {
            if((mDateFrags.get(i).getDate()[0] == date[0]) && (mDateFrags.get(i).getDate()[1] == date[1]) && (mDateFrags.get(i).getDate()[2] == date[2])) {
                Fragment fragment = new ViewPagerFragment();
                ArrayList<Exercise> exercises = mDateFrags.get(i).getExercise();
                // get new exercises if date matches date when entered addExercisesActivity
                if(((mItemDate[0] == date[0]) && (mItemDate[1] == date[1]) && (mItemDate[2] == date[2])) && exercises.size() > 0) {
                    exercises.addAll((mExercises));
                    mDateFrags.add(i, new DateFrag(date, mExercises));
                }
                bundle.putParcelableArrayList("attached_exercises", exercises);
                fragment.setArguments(bundle);
                return fragment;
            }
        }
        // If fragment not in previous records, create new
        Fragment fragment = new ViewPagerFragment();
        // If date matches date when entered addExerciseActivity, attach exercises
        if((mItemDate[0] == date[0]) && (mItemDate[1] == date[1]) && (mItemDate[2] == date[2]) && (mExercises.size() != 0)) {
            Bundle bundle2 = new Bundle();
            ArrayList<Exercise> stored_exercises = mExercises;
            DateFrag newFrag = new DateFrag(date, stored_exercises);
            bundle2.putParcelableArrayList("attached_exercises", stored_exercises);
            fragment.setArguments(bundle2);
            mDateFrags.add(newFrag);
        }
        return fragment;
    }

    // Returns number of fragments in adapter
    @Override
    public int getCount() {
        int[] date = DataHolder.getInstance().date;

        return Integer.MAX_VALUE;
    }

    // Returns page title for each fragment
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Calendar CALENDAR = Calendar.getInstance();
        CALENDAR.add(Calendar.DATE, position);
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(CALENDAR.getTime());
        return "          " + date.substring(0, date.length() - 6) + "          ";
    }
}

