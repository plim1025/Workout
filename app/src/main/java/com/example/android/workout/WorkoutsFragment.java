package com.example.android.workout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;

public class WorkoutsFragment extends Fragment {
    private Calendar CALENDAR;
    private String DATE;
    private TextView DATEVIEW;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workout_tab, container, false);

        // Set Date
        DATEVIEW = view.findViewById(R.id.date);
        CALENDAR = Calendar.getInstance();
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);

        // When swipe right/left date changes accordingly
        View workout_tab = view.findViewById(R.id.workout_tab);
        workout_tab.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeRight() {
                decrementDate();
            }
            public void onSwipeLeft() {
                incrementDate();
            }
        });

        // Make arrow buttons change date
        final ImageButton leftArrow = view.findViewById(R.id.left_arrow);
        final ImageButton rightArrow = view.findViewById(R.id.right_arrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementDate();
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDate();
            }
        });

        return view;
    }

    private void incrementDate() {
        CALENDAR.add(Calendar.DATE, 1);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);
    }

    private void decrementDate() {
        CALENDAR.add(Calendar.DATE, -1);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);
    }
}
