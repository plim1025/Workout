package com.example.android.workout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class WorkoutsFragment extends Fragment {

    private View view;
    private Calendar CALENDAR;
    private String DATE;
    private TextView DATEVIEW;
    private ArrayList<Exercise> exercise = DataHolder.getInstance().exercises;
    private ArrayList<DateFrag> fragArrayList = new ArrayList<>();
    private boolean bundleReceived;
    private boolean viewpagerSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.workouts, container, false);

        // Receive workout arraylist from addExerciseActivity (not sure if this works yet, try at home)
        Bundle bundle = this.getArguments();
        ArrayList<Exercise> added_exercises = bundle.getParcelableArrayList("exercises");
        if(added_exercises == null) {
            bundleReceived = false;
        } else {
            bundleReceived = true;
        }


        // Set Date
        DATEVIEW = view.findViewById(R.id.date);
        CALENDAR = Calendar.getInstance();
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
        if(containsDate()) {
            Fragment fragment = getDateFrag().getFragment();
            Bundle bundle2 = new Bundle();
            ArrayList<Exercise> attached_exercises = getDateFrag().getExercise();
            bundle2.putParcelableArrayList("attached_exercises", attached_exercises);
            fragment.setArguments(bundle);
        } else if (bundleReceived) {
            Fragment fragment = new ViewPagerFragment();
            fragArrayList.add(new DateFrag(CALENDAR, fragment, exercise));
        }

        // Set viewpager
        ViewPager viewPager = view.findViewById(R.id.workouts_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), getContext(), fragArrayList);
        viewPager.setAdapter(adapter);
        viewpagerSet = true;

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
                timerDialog.show(getActivity().getSupportFragmentManager(), null);
            }
        });

        return view;
}

    private void changeDate(int i) {
        CALENDAR.add(Calendar.DATE, i);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);
    }

    @Override
    public String toString() {
        return WorkoutsFragment.class.getSimpleName();
    }

    private boolean containsDate() {
        for (int i = 0; i < fragArrayList.size(); i++) {
            if (fragArrayList.get(i).getDate() == CALENDAR) {
                return true;
            }
        }
        return false;
    }

    private DateFrag getDateFrag() {
        for (int i = 0; i < fragArrayList.size(); i++) {
            if (fragArrayList.get(i).getDate() == CALENDAR) {
                return fragArrayList.get(i);
            }
        }
        Log.e(TAG, "DateFrag not found");
        return null;
    }
}