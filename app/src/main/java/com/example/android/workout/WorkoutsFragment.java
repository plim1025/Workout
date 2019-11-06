package com.example.android.workout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutsFragment extends Fragment {

    private View view;
    private Calendar CALENDAR;
    private String DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
    private TextView DATEVIEW;
    private ArrayList<Exercise> exercise = DataHolder.getInstance().exercises;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Receive workout arraylist from addExerciseActivity (not sure if this works yet, try at home)
        Bundle bundle = this.getArguments();
        ArrayList<Exercise> added_exercises = bundle.getParcelableArrayList("exercises");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.workouts, container, false);

        // Set Date
        DATEVIEW = view.findViewById(R.id.date);
        CALENDAR = Calendar.getInstance();
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

        // Make arrow buttons change date
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

        // If exercises received from AddExerciseActivity OR Date has already been stored in DateArray, set viewpager
        if()
        // Set viewpager
        ViewPagerAdapter adapter =  new ViewPagerAdapter(getContext(), DATE, exercise);
        ViewPager viewPager = view.findViewById(R.id.workouts_viewpager);
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
                timerDialog.show(getActivity().getSupportFragmentManager(), null);
            }
        });

        return view;
}


    private void changeDate(int i) {
        CALENDAR.add(Integer.parseInt(DATE), i);
        DATEVIEW.setText(DATE);
    }

    @Override
    public String toString() {
        return WorkoutsFragment.class.getSimpleName();
    }
}
