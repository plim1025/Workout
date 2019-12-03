package com.example.android.workout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.progress_fragment, container, false);

        // Create dataSet of muscle groups
        ArrayList<PieEntry> muscleGroups = new ArrayList<>();
        muscleGroups.add(new PieEntry(10, "Abs"));
        muscleGroups.add(new PieEntry(15, "Back"));
        muscleGroups.add(new PieEntry(10, "Biceps"));
        muscleGroups.add(new PieEntry(5, "Cardio"));
        muscleGroups.add(new PieEntry(20, "Chest"));
        muscleGroups.add(new PieEntry(5, "Lower Legs"));
        muscleGroups.add(new PieEntry(5, "Triceps"));
        muscleGroups.add(new PieEntry(25, "Upper Legs"));
        PieDataSet pieDataSet = new PieDataSet(muscleGroups, "Muscle Groups");

        // Add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);

        // Set dataset to piechart
        PieData data = new PieData(pieDataSet);
        PieChart chart = view.findViewById(R.id.progress_piechart);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        // Add Menu Button
        final ImageButton imageButton = view.findViewById(R.id.progress_sort_button);
        final PopupMenu dropDownMenu = new PopupMenu(getContext(), imageButton);
        final Menu menu = dropDownMenu.getMenu();

        menu.add("Sets per Exercise");
        menu.add("Sets per Muscle Group");
        menu.add("Total Weight per Muscle Group");

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public String toString() {
        return ProgressFragment.class.getSimpleName();
    }
}
