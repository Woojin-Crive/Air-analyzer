package com.danbi.second;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TwoFragment extends Fragment {
    @Nullable
    BarChart bar;
    TextView textView;
    List<Map> ListMap = new ArrayList<>();

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_two, container, false);
        ListMap = ((MainActivity) Objects.requireNonNull(getActivity())).ListMap;
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        textView = view.findViewById(R.id.textView);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}

//bar = view.findViewById(R.id.bar);
//        List<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0f, Float.parseFloat(Objects.requireNonNull(ListMap.get(0).get("pm1_0")).toString()), "PM 1.0"));
//        entries.add(new BarEntry(1f, Float.parseFloat(Objects.requireNonNull(ListMap.get(0).get("methanen")).toString()), "Methanen"));
//        entries.add(new BarEntry(2f, Float.parseFloat(Objects.requireNonNull(ListMap.get(0).get("Co")).toString()), "Co"));
//        entries.add(new BarEntry(3f, Float.parseFloat(Objects.requireNonNull(ListMap.get(0).get("pm10")).toString()), "PM 10"));
//        entries.add(new BarEntry(4f, Float.parseFloat(Objects.requireNonNull(ListMap.get(0).get("Temp")).toString()), "Temp"));
////        entries.add(new BarEntry(5f, Float.parseFloat(ListMap.get(0).get("Humid").toString()), "Humid"));
////        entries.add(new BarEntry(6f, Float.parseFloat(ListMap.get(0).get("gas").toString()), "Gas"));
//
//
//        BarDataSet bSet = new BarDataSet(entries, "Marks");
//        bSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//
//        ArrayList<String> barFactors = new ArrayList<>();
//        barFactors.add("PM 1.0");
//        barFactors.add("Methanen");
//        barFactors.add("Co");
//        barFactors.add("PM 10");
//        barFactors.add("Temp");
////        barFactors.add("Humid");
////        barFactors.add("Gas");
//
//        XAxis xAxis = bar.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        BarData data = new BarData(bSet);
//        data.setBarWidth(0.6f); // set custom bar width
//        data.setValueTextSize(12);
//        bar.setData(data);
//        bar.setFitBars(true); // make the x-axis fit exactly all bars
//        bar.invalidate(); // refresh
//        bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barFactors));
//
//        Legend l = bar.getLegend();
//        l.setFormSize(10f); // set the size of the legend forms/shapes
//        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
//        l.setTextSize(12f);
//        l.setTextColor(Color.BLACK);
//        List<LegendEntry> lentries = new ArrayList<>();
//        for (int i = 0; i < barFactors.size(); i++) {
//        LegendEntry entry = new LegendEntry();
//        entry.formColor = ColorTemplate.VORDIPLOM_COLORS[i];
//        entry.label = barFactors.get(i);
//        lentries.add(entry);
//        }
//
//        l.setXEntrySpace(10f); // set the space between the legend entries on the x-axis
//        l.setYEntrySpace(10f);
//        l.setCustom(lentries);