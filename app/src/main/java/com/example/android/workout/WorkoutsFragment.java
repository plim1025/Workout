package com.example.android.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static java.util.Calendar.YEAR;

public class WorkoutsFragment extends Fragment {

    private Context mContext = getContext();
    private Calendar CALENDAR = Calendar.getInstance();
    private String DATE;
    private TextView DATEVIEW;
    private int[] date = DataHolder.getInstance().date;
    private ArrayList<Exercise> exercise = DataHolder.getInstance().exercises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<DateFrag> fragArrayList = new ArrayList<DateFrag>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workouts, container, false);

        /*
        // Receive workout arraylist from addExerciseActivity (not sure if this works yet, try at home)
        Bundle bundle = this.getArguments();
        //ArrayList<Exercise> added_exercises = bundle.getParcelableArrayList("exercises");
        if(bundle == null) {
            bundleReceived = false;
        } else {
            bundleReceived = true;
        }
         */



        // Set Date
        DATEVIEW = view.findViewById(R.id.date);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);

        // When swipe right/left date changes accordingly
        View workout_dates = view.findViewById(R.id.workouts);
        workout_dates.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeRight() {
                changeDate(-1);
            }
            public void onSwipeLeft() {
                changeDate(1);
            }
        });

        // Make arrow buttons change date (also implement long clicks to change week)
        final ImageButton leftArrow = view.findViewById(R.id.left_arrow);
        final ImageButton rightArrow = view.findViewById(R.id.right_arrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate(-1);
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate(1);
            }
        });
        leftArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                changeDate(-7);
                return true;
            }
        });
        rightArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                changeDate(7);
                return true;
            }
        });

        // If fragment already inside fragArrayList, get frag, else make new one - also attach exercises to each fragment
        if(containsDate(fragArrayList)) {
            ArrayList<Exercise> attached_exercises = getDateFrag(fragArrayList).getExercise();
            attached_exercises.addAll(exercise);
            /*bundle2.putParcelableArrayList("attached_exercises", attached_exercises);
            fragment.setArguments(bundle);
            */
        } else {
            fragArrayList.add(new DateFrag(date, exercise));
        }

        // Set viewpager
        ViewPager viewPager = view.findViewById(R.id.workouts_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), mContext, fragArrayList);
        viewPager.setAdapter(adapter);

        // Go to AddExerciseActivity
        FloatingActionButton fab1 = view.findViewById(R.id.workout_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddExerciseActivity.class));
            }
        });

        // Set "Copy Workout" FAB to calendar
        FloatingActionButton fab2 = view.findViewById(R.id.workout_fab_2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), com.example.android.workout.CalendarActivity.class));
            }
        });


        // Set "Add Rest" to add rest
        FloatingActionButton fab3 = view.findViewById(R.id.workout_fab_3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerDialog timerDialog = new TimerDialog();
                timerDialog.show(getFragmentManager(), null);
            }
        });

        return view;
}

    public void changeDate(int i) {
        CALENDAR.add(Calendar.DATE, i);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        date[0] += i;
        DATEVIEW.setText(DATE);
    }

    @Override
    public String toString() {
        return WorkoutsFragment.class.getSimpleName();
    }

    private boolean containsDate(ArrayList<DateFrag> fragArrayList) {
        for (int i = 0; i < fragArrayList.size(); i++) {
            if (fragArrayList.get(i).getDate() == date) {
                return true;
            }
        }
        return false;
    }

    private DateFrag getDateFrag(ArrayList<DateFrag> fragArrayList) {
        for (int i = 0; i < fragArrayList.size(); i++) {
            if (fragArrayList.get(i).getDate() == date) {
                return fragArrayList.get(i);
            }
        }
        Log.e(TAG, "DateFrag not found");
        return null;
    }
}