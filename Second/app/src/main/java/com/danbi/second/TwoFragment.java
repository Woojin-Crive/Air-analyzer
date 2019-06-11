package com.danbi.second;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TwoFragment extends Fragment {
    TextView textView;
    TextView unitText;
    List<Map> ListMap = new ArrayList<>();
    private LineChart chart;
    private LineChart chart2;
    private LineChart chart3;
    private LineChart chart4;
    LinearLayout chartLayout1;
    LinearLayout chartLayout2;
    LinearLayout chartLayout3;
    LinearLayout chartLayout4;
    String dataSelector = "pm10";
    int maxIndex = 0;
    public TextView oneMonth;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_two, container, false);
        unitText = view.findViewById(R.id.unitText);
        chart = view.findViewById(R.id.chart1);
        chart2 = view.findViewById(R.id.chart2);
        chart3 = view.findViewById(R.id.chart3);
        chart4 = view.findViewById(R.id.chart4);
        chartLayout1 = view.findViewById(R.id.chartFrame1);
        chartLayout2 = view.findViewById(R.id.chartFrame2);
        chartLayout3 = view.findViewById(R.id.chartFrame3);
        chartLayout4 = view.findViewById(R.id.chartFrame4);
        chartLayout1.setVisibility(View.GONE);
        chartLayout2.setVisibility(View.GONE);
        chartLayout3.setVisibility(View.GONE);
        chartLayout4.setVisibility(View.GONE);
        chartSet(chart);
        chartSet(chart2);
        chartSet(chart3);
        chartSet(chart4);
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
                switch (tab.getPosition()) {
                    case 0:
                        unitText.setText("단위 : μg/m³");
                        dataSelector = "pm10";
                        chartChanged();
                        break;
                    case 1:
                        unitText.setText("단위 : μg/m³");
                        dataSelector = "pm1_0";
                        chartChanged();
                        break;
                    case 2:
                        unitText.setText("단위 : ppm");
                        dataSelector = "gas";
                        chartChanged();
                        break;
                    case 3:
                        unitText.setText("단위 : ppv");
                        dataSelector = "Co";
                        chartChanged();
                        break;
                    case 4:
                        unitText.setText("단위 : °C");
                        dataSelector = "Temp";
                        chartChanged();
                        break;
                    case 5:
                        unitText.setText("단위 : %RH");
                        dataSelector = "Humid";
                        chartChanged();
                        break;
                    case 6:
                        unitText.setText("단위 : 점");
                        dataSelector = "Discomfort";
                        chartChanged();
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
        ySet(chart);
        ySet(chart2);
        ySet(chart3);
        ySet(chart4);
        if (ListMap.size() != 0) {
            unitText.setText("단위 : μg/m³");
            unitText.setVisibility(View.VISIBLE);
            chartChanged();
        } else {
            unitText.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public void chartChanged() {
        setData(chart);
        setData(chart2);
        setData(chart3);
        setData(chart4);
        chart.invalidate();
        chart2.invalidate();
        chart3.invalidate();
        chart4.invalidate();
    }

    public void ySet(LineChart chartName) {
        YAxis yAxis;
        {
            yAxis = chartName.getAxisLeft();
            chartName.getAxisRight().setEnabled(false);
            yAxis.enableGridDashedLine(10f, 10f, 0f);
            yAxis.setSpaceBottom(50f);
            yAxis.setSpaceTop(50f);
        }
        XAxis xAxis;
        xAxis = chartName.getXAxis();
        chartName.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        Legend l = chartName.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setEnabled(false);
        if (chartName == chart) {
            final String[] xAxisString = new String[7];
//            chart.getXAxis().setLabelCount(7);
            for (int i = 0; i < 7; i++) {
                xAxisString[i] = (7 - i) + "일 전";
            }
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    // return the string va
                    return xAxisString[(int) value];
                }
            });
        } else if (chartName == chart2) {
            xAxis.setLabelRotationAngle(-45);
            final String[] xAxisString = new String[9];
//            chart.getXAxis().setLabelCount(7);
            for (int i = 0; i < 8; i++) {
                xAxisString[i] = (24 - i * 3) + "시간 전";
            }
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    // return the string va
                    return xAxisString[(int) value];
                }
            });
        } else if (chartName == chart3) {
            final String[] xAxisString = new String[9];
//            chart.getXAxis().setLabelCount(7);
            for (int i = 0; i < 4; i++) {
                xAxisString[i] = (4 - i) + "주 전";
            }
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    // return the string va
                    return xAxisString[(int) value];
                }
            });
        }
    }

    public void chartSet(LineChart chartName) {
        {
            chartName.setBackgroundColor(Color.WHITE);
            chartName.getDescription().setEnabled(false);
            chartName.setTouchEnabled(false);
            chartName.setDrawGridBackground(false);
            chartName.setDragEnabled(true);
            chartName.setScaleEnabled(true);
            chartName.setPinchZoom(true);
        }
    }

    public ArrayList<Entry> parsing(LineChart chartName) {
        ArrayList<Entry> values = new ArrayList<>();
        if (chartName == chart) {
            int cnt = 0;
            if (maxIndex >= 7)
                cnt = 7; //how many weeks?
            else if (maxIndex > 1) {
                cnt = maxIndex;
                oneMonth.setText(maxIndex + "일 전");
            } else {
                oneMonth.setText("오늘");//bluetooth
            }
            if (maxIndex > 1) {
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
            chartLayout1.setVisibility(View.VISIBLE);
        } else if (chartName == chart2) {
            int cnt = 8;
            for (int j = ListMap.size() - 1; cnt > 0; cnt--) {
                float sum = 0;
                for (int i = 0; i < 3; i++) {
                    sum += Float.parseFloat(ListMap.get(j).get(dataSelector).toString());
                    j--;
                }
                sum /= 3.0;
                values.add(new Entry(cnt - 1, sum));
            }
            Collections.reverse(values);
            Log.d("done", values.toString());
            chartLayout2.setVisibility(View.VISIBLE);
        } else if (chartName == chart3 && maxIndex >= 28) {
            int cnt = 4;
            for (int j = ListMap.size() - 1; cnt > 0; cnt--) {
                float sumAll = 0;
                for (int c = 0; c < 7; c++) {
                    float sum = 0;
                    String data = ListMap.get(j).get("DataTime").toString().substring(0, 11);
                    Log.d("Date", ListMap.get(j).get("DataTime").toString().substring(0, 11));
                    int i;
                    for (i = 0; j > -1 && data.equals(ListMap.get(j).get("DataTime").toString().substring(0, 11)); i++) {
                        sum += Float.parseFloat(ListMap.get(j).get(dataSelector).toString());
                        j--;
                    }
                    sum /= i * 1.0;
                    Log.d("Data amount", String.valueOf(i));
                    sumAll += sum;
                }
                values.add(new Entry(cnt - 1, sumAll/7));
                Log.d("cnt", String.valueOf(cnt));
            }
            Collections.reverse(values);
            Log.d("done", values.toString());
            chartLayout3.setVisibility(View.VISIBLE);
        }
        return values;
    }

    @SuppressLint("SetTextI18n")
    private void setData(final LineChart chartName) {
        ArrayList<Entry> values = parsing(chartName);
        if (ListMap.size() != 0) {
            LineDataSet set1;
            parsing(chartName);
            if (chartName.getData() != null &&
                    chartName.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) chartName.getData().getDataSetByIndex(0);
                set1.setValues(values);
                set1.notifyDataSetChanged();
                chartName.getData().notifyDataChanged();
                chartName.notifyDataSetChanged();
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
                        return chartName.getAxisLeft().getAxisMinimum();
                    }
                });

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1); // add the data sets

                // create a data object with the data sets
                LineData data = new LineData(dataSets);

                // set data
                chartName.setData(data);
            }
        }
    }
}
