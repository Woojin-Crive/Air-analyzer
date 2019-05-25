package com.danbi.second;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TwoFragment extends Fragment {
    TextView textView;
    List<Map> ListMap = new ArrayList<>();
    private LineChart chart;
    String dataSelector = "pm10";
    float mini = 0f, maxi = 200f;
    int maxIndex = 0;
    public TextView oneMonth;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_two, container, false);
        oneMonth = view.findViewById(R.id.oneMonth);
        ListMap = ((MainActivity) Objects.requireNonNull(getActivity())).ListMap;
        int j;
        for (int i = 0; i < ListMap.size() - 1; ) {
            String data = ListMap.get(i).get("DataTime").toString().substring(0, 11);
            for (j = i; j < ListMap.size() && data.equals(ListMap.get(j).get("DataTime").toString().substring(0, 11)); j++) {
            }
            maxIndex++;
            Log.d("index", maxIndex + " : " + data);
            i = j;
        }
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        textView = view.findViewById(R.id.textView);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(getActivity(), String.format("tab %d selected", tab.getPosition()), Toast.LENGTH_LONG).show();
                switch (tab.getPosition()) {
                    case 0:
                        dataSelector = "pm10";
                        setData();
                        chart.invalidate();
                        break;
                    case 1:
                        dataSelector = "pm1_0";
                        setData();
                        chart.invalidate();
                        break;
                    case 2:
                        dataSelector = "gas";
                        setData();
                        chart.invalidate();
                        break;
                    case 3:
                        dataSelector = "Co";
                        setData();
                        chart.invalidate();
                        break;
                    case 4:
                        dataSelector = "Temp";
                        setData();
                        chart.invalidate();
                        break;
                    case 5:
                        dataSelector = "Humid";
                        setData();
                        chart.invalidate();
                        break;
                    case 6:
                        dataSelector = "Discomfort";
                        setData();
                        chart.invalidate();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        {   // // Chart Style // //
            chart = view.findViewById(R.id.chart1);

            // background color
            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // set listeners
            chart.setDrawGridBackground(false);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }
        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }
        ySet(maxi, mini);
        // add data
        setData();

        // draw points over time
        //chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
        l.setEnabled(false);
        return view;
    }

    public void ySet(float ymaxi, float ymini) {
        YAxis yAxis;
        {
            yAxis = chart.getAxisLeft();
            chart.getAxisRight().setEnabled(false);
            yAxis.enableGridDashedLine(10f, 10f, 0f);
            yAxis.setSpaceBottom(50f);
            yAxis.setSpaceTop(50f);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (ListMap.size() != 0) {
            ArrayList<Entry> values = new ArrayList<>();
            int cnt = 0;
            if (maxIndex >= 7)
                cnt = 7; //how many weeks?
            else if (maxIndex >= 1) {
                cnt = maxIndex;
                oneMonth.setText(maxIndex + "일 전");
            } else {
                oneMonth.setText("오늘");//bluetooth
            }
            if (maxIndex >= 1) {
                for (int j = ListMap.size() - 1; cnt > 0; ) {
                    float sum = 0;
                    String data = ListMap.get(j).get("DataTime").toString().substring(0, 11);
                    Log.d("Date", ListMap.get(j).get("DataTime").toString().substring(0, 11));
                    int i;
                    for (i = 0; j > -1 && data.equals(ListMap.get(j).get("DataTime").toString().substring(0, 11)); i++) {
                        sum += Float.parseFloat(ListMap.get(j).get(dataSelector).toString());
                        j--;
                    }
                    //Log.d("j", String.valueOf(j));
                    sum /= i * 1.0;
                    Log.d("Data amount", String.valueOf(i));
                    if (mini > sum / i * 1.0)
                        mini = (float) (sum / i * 1.0);
                    if (maxi > sum / i * 1.0)
                        maxi = (float) (sum / i * 1.0);

                    values.add(new Entry(cnt - 1, sum));
                    cnt--;
                }
                Collections.reverse(values);
                Log.d("done", values.toString());
            } else {//bluetooth
                for (int i = 0; i < ListMap.size() - 1; i++) {
                    values.add(new Entry(i, Float.parseFloat(ListMap.get(i).get(dataSelector).toString())));
                }
            }
            LineDataSet set1;

            if (chart.getData() != null &&
                    chart.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                set1.notifyDataSetChanged();
                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                // create a dataset and give it a type
                set1 = new LineDataSet(values, "DataSet 1");

                set1.setDrawIcons(false);

                // draw dashed line
                set1.enableDashedLine(10f, 5f, 0f);

                // black lines and points
                set1.setColor(Color.BLACK);
                set1.setCircleColor(Color.BLACK);

                // line thickness and point size
                set1.setLineWidth(1f);
                set1.setCircleRadius(3f);

                // draw points as solid circles
                set1.setDrawCircleHole(false);

                //customize legend entry
//            set1.setFormLineWidth(.0f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(.0f);

                // text size of values
                set1.setValueTextSize(9f);
                // draw selection line as dashed
                set1.enableDashedHighlightLine(10f, 5f, 0f);

                // set the filled area
                set1.setDrawFilled(true);
                set1.setFillFormatter(new IFillFormatter() {
                    @Override
                    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                        return chart.getAxisLeft().getAxisMinimum();
                    }
                });

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1); // add the data sets

                // create a data object with the data sets
                LineData data = new LineData(dataSets);

                // set data
                chart.setData(data);
            }
        }
    }
}
