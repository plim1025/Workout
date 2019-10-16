package com.example.android.workout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    private static final String ACTIVITY_NAME = MainActivity.class.getSimpleName();
    private static final String TAG = ACTIVITY_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fragmentManager = getSupportFragmentManager();

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set home fragment to workout tab
        Fragment initialFragment = new WorkoutsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, initialFragment, "0");
        fragmentTransaction.addToBackStack("Add " + initialFragment.toString());
        fragmentTransaction.commit();

        // Creates bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.nav_workout:
                        fragment = new WorkoutsFragment();
                        break;
                    case R.id.nav_exercises:
                        fragment = new ExercisesFragment();
                        break;
                    case R.id.nav_progress:
                        fragment = new ProgressFragment();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, "0");
                // Need to title it so that fragment tier 2 can return to tier 1 after pressing android back button
                fragmentTransaction.addToBackStack("Add main");
                fragmentTransaction.commit();
                return true;
            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                StringBuilder backstackEntryMessage = new StringBuilder("Current status of fragment transaction back stack: " + fragmentManager.getBackStackEntryCount() + "\n");

                for (int index = (fragmentManager.getBackStackEntryCount() - 1); index >= 0; index--) {
                    FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(index);
                    backstackEntryMessage.append(entry.getName() + "\n");
                }
                Log.i(TAG, backstackEntryMessage.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_calendar:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.dropdown_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.dropdown_themes:
                startActivity(new Intent(this, ThemesActivity.class));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
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

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        // If press back button on fragment tier 1, return to home and pause app
        if (fragment == fragmentManager.findFragmentByTag("0")) {
            fragmentManager.popBackStack(0,0);
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);

            //  If press back button on fragment tier 2, replace frame with fragment tier 2 by searching with tag 0 and pop backstack up until last fragment tier 1
        } else if (fragment == fragmentManager.findFragmentByTag("1")) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragmentManager.findFragmentByTag("0"), "0");
            fragmentTransaction.addToBackStack("Add " + fragment.toString());
            fragmentTransaction.commit();
            fragmentManager.popBackStack("Add main", 0);
        }
    }
}
