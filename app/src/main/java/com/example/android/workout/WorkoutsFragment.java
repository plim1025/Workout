package com.example.android.workout;

import android.content.Intent;
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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutsFragment extends Fragment {
    private Calendar CALENDAR;
    private String DATE;
    private TextView DATEVIEW;
    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Exercise> exercise = new ArrayList<>();
    private View view;
    private boolean buildRecycler = false;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("exercise", exercise);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            buildRecycler = true;
            exercise = savedInstanceState.getParcelableArrayList("exercise");
        }
        // Receive workout arraylist from addExerciseActivity
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            buildRecycler = true;
            ArrayList<Exercise> added_exercises = bundle.getParcelableArrayList("added_exercises");
            exercise.addAll(added_exercises);
        }
        if (buildRecycler) {
            buildRecyclerView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.workout, container, false);

        // Set Date
        DATEVIEW = view.findViewById(R.id.date);
        CALENDAR = Calendar.getInstance();
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);

        // When swipe right/left date changes accordingly
        View workout_tab = view.findViewById(R.id.workout_tab);
        workout_tab.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
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

        // Set "Add Exercise" FAB to exercises and switch exercises icon color to blue
        FloatingActionButton fab1 = view.findViewById(R.id.workout_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                getFragmentManager().beginTransaction().replace(R.id.frame, new ExercisesFragment()).commit();
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

    private void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.workout_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mAdapter = new WorkoutsRecyclerViewAdapter(exercise);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public String toString() {
        return WorkoutsFragment.class.getSimpleName();
    }
}
