package com.example.android.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;

import static java.util.Calendar.DATE;

public class ViewPagerFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


    private void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.workout_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mAdapter = new WorkoutsRecyclerViewAdapter(exercise);

        // When recyclerView built, enable swipe right/left
        mRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeLeft() {
                changeDate(1);
            }
            public void onSwipeRight() {
                changeDate(-1);
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}

