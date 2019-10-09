package com.example.android.workout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static android.content.ContentValues.TAG;


public class ExercisesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.exercises_tab, container, false);

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

        menu.add("By Category");
        menu.add("By Most Recent");
        menu.add("Favorites");

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.show();
            }
        });

        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        // item ID 0 was clicked
                        return true;
                    case 1:
                        // item ID 1 was clicked
                        return true;
                    case 2:
                        // item ID 2 was clicked
                        return true;
                }
                return false;
            }
        });

        try {
            JSONObject JSON = new JSONObject(readJSONFromAsset());
            JSONObject exercises = JSON.getJSONObject("exercise_info");
            ArrayList<Exercise> exercise = new ArrayList<Exercise>();
            exercises.getJSONObject
            for(int i = 0; i < exercises.names().length(); i++){
                exercise.add(new Exercise(exercises.names().getString(i), exercises.getString("Main Muscle Group"), exercises.getString("Type"), exercises.getString("Equipment")));
            }
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, exercise);
            ListView exerciseListView = view.findViewById(R.id.exercise_list_view);
            exerciseListView.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public String toString() {
        return ExercisesFragment.class.getSimpleName();
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("exercise_info");
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
