package com.example.android.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.Calendar;

public class WorkoutsTab extends AppCompatActivity {

    private Calendar CALENDAR;
    private String DATE;
    private TextView DATEVIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_tab);

        // Set Date
        DATEVIEW = findViewById(R.id.date);
        CALENDAR = Calendar.getInstance();
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);

        // When swipe right/left date changes accordingly
        View workout_tab = findViewById(R.id.workout_tab);
        workout_tab.setOnTouchListener(new OnSwipeTouchListener(WorkoutsTab.this) {
            public void onSwipeRight() {
                decrementDate();
            }
            public void onSwipeLeft() {
                incrementDate();
            }
        });

        // Make arrow buttons change date
        final ImageButton leftArrow = findViewById(R.id.left_arrow);
        final ImageButton rightArrow = findViewById(R.id.right_arrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementDate();
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDate();
            }
        });

        // Creates bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_workout:
                        break;
                    case R.id.nav_exercises:
                        Intent a = new Intent(WorkoutsTab.this, ExercisesTab.class);
                        startActivity(a);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_progress:
                        Intent b = new Intent(WorkoutsTab.this, ProgressTab.class);
                        startActivity(b);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void incrementDate() {
        CALENDAR.add(Calendar.DATE, 1);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);
    }

    private void decrementDate() {
        CALENDAR.add(Calendar.DATE, -1);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);
    }
}
