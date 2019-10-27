package com.example.android.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ProgressFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.progress, container, false);

        // Add pie chart slices
        PieChartView pieChartView = view.findViewById(R.id.pie_chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieRed)).setLabel("1"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieOrange)).setLabel("2"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieYellow)).setLabel("3"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieLime)).setLabel("4"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieGreen)).setLabel("5"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieCyan)).setLabel("6"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieBlue)).setLabel("7"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.piePurple)).setLabel("8"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieMagenta)).setLabel("9"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.pieGrey)).setLabel("10"));

        // Set pie chart
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true).setCenterText1("Whatever is selected in sort here").setCenterText1FontSize(20);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setChartRotationEnabled(false);

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
