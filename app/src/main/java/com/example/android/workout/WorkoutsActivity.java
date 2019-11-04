package com.example.android.workout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutsActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    private static final String ACTIVITY_NAME = WorkoutsActivity.class.getSimpleName();
    private static final String TAG = ACTIVITY_NAME;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Exercise> exercises_to_send = new ArrayList<>();
    private Bundle bundle = new Bundle();

    private Calendar CALENDAR;
    private String DATE;
    private TextView DATEVIEW;
    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Exercise> exercise = DataHolder.getInstance().exercises;
    private boolean buildRecycler = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.workouts_activity);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Receive workout arraylist from addExerciseActivity
        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            buildRecycler = true;
            ArrayList<Exercise> added_exercises = bundle.getParcelableArrayList("exercises");
            if(added_exercises!=null) {
                exercise.addAll(added_exercises);
            }
        }

         */
        if (buildRecycler) {
            buildRecyclerView();
        }

        // Set Date
        DATEVIEW = findViewById(R.id.date);
        CALENDAR = Calendar.getInstance();
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);

        // When swipe right/left date changes accordingly
        View workout_tab = findViewById(R.id.frame);
        workout_tab.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                changeDate(-1);
            }
            public void onSwipeLeft() {
                changeDate(1);
            }
        });

        // Make arrow buttons change date
        final ImageButton leftArrow = findViewById(R.id.left_arrow);
        final ImageButton rightArrow = findViewById(R.id.right_arrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate(-1);
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate(1);
            }
        });
        leftArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                changeDate(-7);
                return true;
            }
        });
        rightArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                changeDate(7);
                return true;
            }
        });

        // Set "Add Exercise" FAB to exercises and switch exercises icon color to blue
        FloatingActionButton fab1 = findViewById(R.id.workout_fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddExerciseActivity.class));
            }
        });

        // Set "Copy Workout" FAB to calendar
        FloatingActionButton fab2 = findViewById(R.id.workout_fab_2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.example.android.workout.CalendarActivity.class));
            }
        });


        // Set "Add Rest" to add rest
        FloatingActionButton fab3 = findViewById(R.id.workout_fab_3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerDialog timerDialog = new TimerDialog();
                timerDialog.show(getSupportFragmentManager(), null);
            }
        });

        // Creates bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();

        /*// Receive parcelable ArrayList from AddExerciseSetsActivity and send to WorkoutsFragment
        ArrayList<Exercise> exercise = getIntent().getParcelableArrayListExtra("exercise");
        if(exercise!=null) {
            exercises_to_send.addAll(exercise);
            bundle.putParcelableArrayList("exercises", exercises_to_send);
            workoutFragment.setArguments(bundle);
        }*/

        // Set initial fragment to workouts
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_workout:
                        break;
                    case R.id.nav_exercises:
                        fragment = new ExercisesFragment();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment, "0");
                        break;
                    case R.id.nav_progress:
                        fragment = new ProgressFragment();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment, "0");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
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

    private void changeDate(int i) {
        CALENDAR.add(Calendar.DATE, i);
        DATE = DateFormat.getDateInstance(DateFormat.LONG).format(CALENDAR.getTime());
        DATEVIEW.setText(DATE);
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.workout_recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new WorkoutsRecyclerViewAdapter(exercise);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
