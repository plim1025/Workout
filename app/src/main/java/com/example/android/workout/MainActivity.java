package com.example.android.workout;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // When calendar icon selected, calendar fragment displayed, and if selected again while in calendar fragment, toast message displayed
        if(item.getItemId()==R.id.nav_calendar) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("CALENDAR");
            if(currentFragment != null && currentFragment.isVisible()){
                Toast.makeText(getApplicationContext(), "Today selected", Toast.LENGTH_SHORT).show();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CalendarFragment(), "CALENDAR").commit();
        }
        // When settings icon selected, settings fragment displayed
        else if(item.getItemId()==R.id.dropdown_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SettingsFragment()).commit();
        }
        else if(item.getItemId()==R.id.dropdown_themes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ThemesFragment()).commit();
        }



        return super.onOptionsItemSelected(item);
    }

    // Creates top navigation menu
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
