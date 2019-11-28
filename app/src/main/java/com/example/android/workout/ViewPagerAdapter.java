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

    // Constructor
    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    // This runs when new fragments need to be loaded into the adapter - loads 3 in at a time
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    // Returns number of fragments in adapter
    @Override
    public int getCount() {
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

