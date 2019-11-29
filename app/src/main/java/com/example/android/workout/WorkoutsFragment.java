package com.example.android.workout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class WorkoutsFragment extends Fragment {

    private ViewPager viewPager;

    @Override
    public void onPause() {
        // Get last position of viewpager and save in shared preferences
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("viewPager position", 0).edit();
        editor.putInt("viewPager position", viewPager.getCurrentItem());
        editor.apply();

        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workoutsFragment, container, false);

        // Set viewpager
        viewPager = view.findViewById(R.id.workouts_viewpager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), this.getActivity());
        int lastPos = getActivity().getSharedPreferences("viewPager position", 0).getInt("viewPager position", 0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(lastPos);

        // Go to AddExerciseActivity
        FloatingActionButton fab1 = view.findViewById(R.id.workout_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExerciseActivity.class);
                // Put position of viewpager as argument
                int date = viewPager.getCurrentItem();
                intent.putExtra("date", date);
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