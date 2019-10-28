package com.example.android.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExercisesFragment extends Fragment {

    View view;
    private int sortIndicator;
    private JSONObject JSON;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.exercises, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Create FAB
        FloatingActionButton fab1 = view.findViewById(R.id.exercises_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateNewExerciseActivity.class));
            }
        });

        // Create menu
        final ImageButton imageButton = view.findViewById(R.id.exercise_sort_button);
        final PopupMenu dropDownMenu = new PopupMenu(getContext(), imageButton);
        final Menu menu = dropDownMenu.getMenu();

        menu.add("Basic List");
        menu.add("Complex List");
        menu.add("By Category");
        menu.add("By Most Recent");
        menu.add("Favorites");

        // Show menu items if click on imageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.show();
            }
        });

        //
        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        sortIndicator = 0; break;
                    case 1:
                        sortIndicator = 1; break;
                    case 2:
                        sortIndicator = 2; break;
                    case 3:
                        sortIndicator = 3; break;
                    case 4:
                        sortIndicator = 4; break;
                }
                buildRecyclerView();
                return false;
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public String toString() {
        return ExercisesFragment.class.getSimpleName();
    }

    private void buildRecyclerView() {
        try {
            switch (sortIndicator) {
                case 0:
                    JSON = new JSONObject(readBasicJSONFromAsset()); break;
                case 1:
                    JSON = new JSONObject(readComplexJSONFromAsset()); break;
                case 2:
                    JSON = new JSONObject(readComplexJSONFromAsset()); break;
                case 3:
                    JSON = new JSONObject(readComplexJSONFromAsset()); break;
            }
            JSONObject exercises = JSON.getJSONObject("exercise_info_complex");
            final ArrayList<Exercise> exercise = new ArrayList<Exercise>();
            JSONArray exercise_names = exercises.names();
            for(int i = 0; i < exercise_names.length(); i++){
                JSONObject exercise_info = exercises.getJSONObject(exercise_names.getString(i));
                exercise.add(new Exercise(exercise_names.getString(i), exercise_info.getString("Main Muscle Group"), exercise_info.getString("Type"), exercise_info.getString("Equipment"), 0, 0, 0));
            }

            RecyclerView recyclerView = view.findViewById(R.id.exercise_recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            ExerciseRecyclerViewAdapter adapter = new ExerciseRecyclerViewAdapter(exercise);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new ExerciseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), AddExerciseActivity.class);
                    intent.putExtra("Exercises", exercise.get(position));
                    startActivity(intent);
                }
            });

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private String readComplexJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("exercise_info_complex");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private String readBasicJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("exercise_info_basic");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
