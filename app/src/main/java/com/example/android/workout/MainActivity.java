package com.example.android.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ValueAnimator;
import android.graphics.drawable.ColorStateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Set home fragment to workout tab
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new WorkoutsFragment()).commit();

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
                        Fragment workouts_tab = new WorkoutsFragment();
                        FragmentTransaction fm1 = getSupportFragmentManager().beginTransaction();
                        fm1.replace(R.id.frame, workouts_tab).commit();
                        return true;
                    case R.id.nav_exercises:
                        Fragment exercises_tab = new ExercisesFragment();
                        FragmentTransaction fm2 = getSupportFragmentManager().beginTransaction();
                        fm2.replace(R.id.frame, exercises_tab).commit();
                        return true;
                    case R.id.nav_progress:
                        Fragment progress_tab = new ProgressFragment();
                        FragmentTransaction fm3 = getSupportFragmentManager().beginTransaction();
                        fm3.replace(R.id.frame, progress_tab).commit();
                        return true;
                }
                return false;
            }
        });
    }

    // When calendar icon selected, calendar fragment displayed, and if selected again while in calendar fragment, toast message displayed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("CALENDAR");
        if(item.getItemId()==R.id.nav_calendar) {
            if(currentFragment != null && currentFragment.isVisible()){
                Toast.makeText(getApplicationContext(), "Today selected", Toast.LENGTH_SHORT).show();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CalendarFragment(), "CALENDAR").commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Creates top navigation menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
