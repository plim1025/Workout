package com.example.android.workout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        View view = inflater.inflate(R.layout.progress_tab, container, false);

        PieChartView pieChartView = view.findViewById(R.id.pie_chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, R.color.black));
        pieData.add(new SliceValue(25, R.color.lightGray));
        pieData.add(new SliceValue(10, Color.RED));
        pieData.add(new SliceValue(60, Color.MAGENTA));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartView.setPieChartData(pieChartData);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public String toString() {
        return ProgressFragment.class.getSimpleName();
    }
}
