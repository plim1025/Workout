package com.example.android.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_tab, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public String toString() {
        return CalendarFragment.class.getSimpleName();
    }
}
