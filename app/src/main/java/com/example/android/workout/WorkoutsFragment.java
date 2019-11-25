package com.example.android.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;


public class WorkoutsFragment extends Fragment {

    private Context mContext = getContext();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workouts, container, false);

        // Set viewpager
        final ViewPager viewPager = view.findViewById(R.id.workouts_viewpager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), mContext);
        viewPager.setAdapter(adapter);


        // Go to AddExerciseActivity
        FloatingActionButton fab1 = view.findViewById(R.id.workout_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExerciseActivity.class);
                // Put position of viewpager as argument
                intent.putExtra("date", viewPager.getCurrentItem());
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