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
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ViewPagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workouts_pager_item, container, false);

        if(getArguments() != null) {
            // Get ArrayList argument sent in WorkoutsFragment
            ArrayList<Exercise> exercises = getArguments().getParcelableArrayList("attached_exercises");
            RecyclerView mRecyclerView = view.findViewById(R.id.workout_recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            WorkoutsRecyclerViewAdapter mAdapter = new WorkoutsRecyclerViewAdapter(exercises);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

        return view;
    }
}

