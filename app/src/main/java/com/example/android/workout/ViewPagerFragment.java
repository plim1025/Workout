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

import java.util.ArrayList;


public class ViewPagerFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Exercise> exercises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.workouts_pager_item, container, false);

        // Get ArrayList argument sent in WorkoutsFragment
        exercises = getArguments().getParcelableArrayList("attached_exercises");

        mRecyclerView = view.findViewById(R.id.workout_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mAdapter = new WorkoutsRecyclerViewAdapter(exercises);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}

