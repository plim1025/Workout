package com.example.android.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {

    private int sortIndicator = 0;
    private JSONObject json;
    private JSONObject exercises;
    private JSONArray exercise_names;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_exercise_activity);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.add_exercise_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Create menu
        final ImageButton imageButton = findViewById(R.id.exercise_sort_button);
        final PopupMenu dropDownMenu = new PopupMenu(this, imageButton);
        final Menu menu = dropDownMenu.getMenu();

        menu.add(Menu.NONE, 0, 0, "Basic List");
        menu.add(Menu.NONE, 1, 1,"Complex List");
        menu.add(Menu.NONE, 2, 2,"By Category");
        menu.add(Menu.NONE, 3, 3,"By Most Recent");
        menu.add(Menu.NONE, 4, 4,"Favorites");

        buildRecyclerView();

        // Show menu items if click on imageButton
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
    }

    private void buildRecyclerView() {
        try {
            switch (sortIndicator) {
                case 0:
                    json = new JSONObject(readBasicJSONFromAsset());
                    exercises = json.getJSONObject("exercise_info");
                    break;
                case 1:
                    json = new JSONObject(readComplexJSONFromAsset());
                    exercises = json.getJSONObject("exercise_info");
                    break;
                case 2:
                    json = new JSONObject(readComplexJSONFromAsset());
                    break;
                case 3:
                    json = new JSONObject(readComplexJSONFromAsset());
                    break;
            }

            final ArrayList<Exercise> exercise = new ArrayList<Exercise>();
            exercise_names = exercises.names();

            for(int i = 0; i < exercise_names.length(); i++){
                JSONObject exercise_info = exercises.getJSONObject(exercise_names.getString(i));
                exercise.add(new Exercise(exercise_names.getString(i), exercise_info.getString("Main Muscle Group"), exercise_info.getString("Type"), exercise_info.getString("Equipment"), 0, 0, 0));
            }

            RecyclerView recyclerView = findViewById(R.id.exercise_recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            ExerciseRecyclerViewAdapter adapter = new ExerciseRecyclerViewAdapter(exercise);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            // When click on item, go to AddExerciseSetsActivity and send which exercise was clicked on
            adapter.setOnItemClickListener(new ExerciseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getApplicationContext(), AddExerciseSetsActivity.class);
                    intent.putExtra("current_exercise", exercise.get(position));
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
            InputStream is = getAssets().open("exercise_info_complex");
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
            InputStream is = getAssets().open("exercise_info_basic");
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
