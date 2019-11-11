package com.example.android.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static java.util.Calendar.YEAR;

public class WorkoutsFragment extends Fragment {

    private Context mContext = getContext();
    private int[] date = DataHolder.getInstance().date;
    private ArrayList<Exercise> exercises = DataHolder.getInstance().exercises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workouts, container, false);

        // Set viewpager
        final ViewPager viewPager = view.findViewById(R.id.workouts_viewpager);
        ArrayList<Exercise> exercises_copy = (ArrayList<Exercise>) exercises.clone();
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), mContext, date, exercises_copy);
        viewPager.setAdapter(adapter);
        exercises.clear();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                Calendar CALENDAR = Calendar.getInstance();
                CALENDAR.add(Calendar.DATE, position);
                date[0] = CALENDAR.get(Calendar.DAY_OF_MONTH);
                date[1] = CALENDAR.get(Calendar.MONTH);
                date[2] = CALENDAR.get(YEAR);
            }
        });

        // Go to AddExerciseActivity
        FloatingActionButton fab1 = view.findViewById(R.id.workout_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExerciseActivity.class);
                startActivity(intent);
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

    @Override
    public String toString() {
        return WorkoutsFragment.class.getSimpleName();
    }
}