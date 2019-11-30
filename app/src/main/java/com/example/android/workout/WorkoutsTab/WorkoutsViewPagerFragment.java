package com.example.android.workout.WorkoutsTab;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.workout.Exercise;
import com.example.android.workout.MainActivity;
import com.example.android.workout.R;
import com.example.android.workout.WorkoutsTab.WorkoutsRecyclerViewAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;


public class WorkoutsViewPagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.workouts_pager_item, container, false);

        final FloatingActionButton fab = view.findViewById(R.id.workouts_pager_fab);
        fab.setVisibility(View.INVISIBLE);

        if(getArguments() != null) {
            // Get ArrayList argument sent in WorkoutsFragment
            ArrayList<Exercise> exercises = getArguments().getParcelableArrayList("attached_exercises");

            // Set recycler view in fragment
            final RecyclerView mRecyclerView = view.findViewById(R.id.workout_recycler_view);
            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            final WorkoutsRecyclerViewAdapter mAdapter = new WorkoutsRecyclerViewAdapter(exercises);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnLongItemClickListener(new WorkoutsRecyclerViewAdapter.OnLongItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    View cardView = mLayoutManager.findViewByPosition(position);
                    cardView.findViewById(R.id.workout_set).setBackgroundColor(getResources().getColor(R.color.lightBlue));
                    enterEditMode(fab);
            }
            });
        }
        return view;
    }

    public void enterEditMode(FloatingActionButton trashIcon) {
        trashIcon.setVisibility(View.VISIBLE);
        trashIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete selected recyclerview items and remove from database
            }
        });

        // Add onClickListener to mAdapter overriding onLongItemClickListener
        // make all items draggable
    }
}

