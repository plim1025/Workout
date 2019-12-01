package com.example.android.workout.WorkoutsTab;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.workout.Exercise;
import com.example.android.workout.R;
import com.example.android.workout.WorkoutData.WorkoutContract;
import com.example.android.workout.WorkoutData.WorkoutDBHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class WorkoutsViewPagerFragment extends Fragment {

    private ArrayList<Exercise> exercises = new ArrayList<>();
    private int date;
    private FloatingActionButton trashFAB;
    private FloatingActionButton checkFAB;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WorkoutsRecyclerViewAdapter mAdapter;
    private WorkoutDBHelper mDbHelper;
    private boolean inEditMode = false;
    // Keeps track of highlighted exercise_items
    private ArrayList<Integer> selected_items = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.workouts_pager_item, container, false);

        if(getArguments() != null) {
            // Instantiate FABs and set invisible
            trashFAB = view.findViewById(R.id.workouts_pager_fab_trash);
            checkFAB = view.findViewById(R.id.workouts_pager_fab_check);
            trashFAB.setVisibility(View.INVISIBLE);
            checkFAB.setVisibility(View.INVISIBLE);

            // Get list of exercises sent from AddExercisesActivity
            exercises = getArguments().getParcelableArrayList("attached_exercises");

            // Get date sent from WorkoutsViewPagerAdapter
            date = getArguments().getInt("date");

            // Set recycler view in fragment
            mRecyclerView = view.findViewById(R.id.workout_recycler_view);
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mAdapter = new WorkoutsRecyclerViewAdapter(exercises);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnLongClickListener(new WorkoutsRecyclerViewAdapter.OnLongItemClickListener() {
                @Override
                public void onItemLongClick(int position) {
                    // If not in edit mode, highlight exercise onLongClick and enter edit mode, else do nothing
                    if(!inEditMode) {
                        View cardView = mLayoutManager.findViewByPosition(position);
                        cardView.findViewById(R.id.workout_set).setBackground(getResources().getDrawable(R.color.lightBlue));
                        selected_items.add(position);
                        enterEditMode();
                    }
                }
            });
            mAdapter.setOnClickListener(new WorkoutsRecyclerViewAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    // If in edit mode, highlight exercise onClick
                    if(inEditMode) {
                        LinearLayout exercise_item = mLayoutManager.findViewByPosition(position).findViewById(R.id.workout_set);
                        int exercise_item_background_color = ((ColorDrawable) exercise_item.getBackground()).getColor();

                        // Check if background color already blue, and if yes, then change back to white, else change color to blue
                        if(exercise_item_background_color == Color.parseColor("#ADD8E6")) {
                            exercise_item.setBackground(getResources().getDrawable(R.color.lightGray));
                            for(int i = 0; i < selected_items.size(); i++) {
                                if(selected_items.get(i) == position) {
                                    selected_items.remove(i);
                                }
                            }
                        } else {
                            exercise_item.setBackground(getResources().getDrawable(R.color.lightBlue));
                            selected_items.add(position);
                        }
                    }
                }
            });
        }
        return view;
    }

    public void enterEditMode() {
        inEditMode = !inEditMode;

        // Show trash and check FABs
        trashFAB.setVisibility(View.VISIBLE);
        checkFAB.setVisibility(View.VISIBLE);
        trashFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWorkouts();
            }
        });
        checkFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitEditMode();
            }
        });

        // make all items draggable
    }

    public void exitEditMode() {
        inEditMode = !inEditMode;

        // Hide trash and check FABs
        trashFAB.setVisibility(View.INVISIBLE);
        checkFAB.setVisibility(View.INVISIBLE);

        // Turns highlighted exercise items back to gray
        for(int i = 0; i < selected_items.size(); i++) {
            mLayoutManager.findViewByPosition(selected_items.get(i)).findViewById(R.id.workout_set).setBackground(getResources().getDrawable(R.color.lightGray));
        }

        selected_items.clear();
    }

    public void deleteWorkouts() {

        // Remove items from recyclerView
        for(int i = 0; i < selected_items.size(); i++) {
            mLayoutManager.removeViewAt(i);
            mAdapter.notifyItemRemoved(i);
        }

        // Instantiate DbHelper
        mDbHelper = new WorkoutDBHelper(this.getActivity());

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT _id FROM workouts WHERE date = ?", new String[] {Integer.toString(date)});

        // Delete all marked exercise items
        for(int i = 0; i < selected_items.size(); i++) {
            cursor.moveToNext();
            String[] id = {Long.toString(cursor.getLong(cursor.getColumnIndex("_id")))};
            db.delete("workouts", "_id = ?", id);
        }
        cursor.close();
    }

    @Override
    public void onPause() {
        exitEditMode();
        super.onPause();
    }
}

