package com.danbi.second;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OneFragment extends Fragment {
    @Nullable
    List<Map> ListMap = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        ListMap = ((MainActivity) Objects.requireNonNull(getActivity())).ListMap;
        if (ListMap.size() != 0) {
            ImageView image1 = view.findViewById(R.id.image1);
            ImageView image2 = view.findViewById(R.id.image2);
            ImageView image3 = view.findViewById(R.id.image3);
            ImageView image4 = view.findViewById(R.id.image4);
            ImageView image5 = view.findViewById(R.id.image5);

            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
            image3.setVisibility(View.VISIBLE);
            image4.setVisibility(View.VISIBLE);
            image5.setVisibility(View.VISIBLE);

            changeImage(image1, getGrade("pm10"));
            changeImage(image2, getGrade("pm1_0"));
            changeImage(image3, getGrade("gas"));
            changeImage(image4, getGrade("Co"));
            changeImage(image5, getGrade("methanen"));

            TextView value1 = view.findViewById(R.id.value1);
            TextView value2 = view.findViewById(R.id.value2);
            TextView value3 = view.findViewById(R.id.value3);
            TextView value4 = view.findViewById(R.id.value4);
            TextView value5 = view.findViewById(R.id.value5);

            value1.setText(getValue("pm10", "μg/m³"));
            value2.setText(getValue("pm1_0", "μg/m³"));
            value3.setText(getValue("gas", "ppm"));
            value4.setText(getValue("Co", "ppm"));
            value5.setText(getValue("methanen", "ppm"));

            TextView airValue = view.findViewById(R.id.airValue);
            int airvalue = (getGrade("pm10") + getGrade("pm1_0") + getGrade("methanen") + getGrade("Co") + getGrade("gas")) * 5;
            airValue.setText(Integer.toString(airvalue));

            ImageButton imageButton = view.findViewById(R.id.imageButton);//기상청 버튼
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://www.weather.go.kr/weather/main.jsp";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });

            TextView textView2 = view.findViewById(R.id.textView2);//현재 시간
            textView2.setText(DateFormat.format("HH:mm", System.currentTimeMillis()));
        }
        return view;
    }

    public String getValue(String dataSelector, String unit) {
        return ListMap.get(ListMap.size() - 1).get(dataSelector).toString() + unit;
    }

    public int getGrade(String dataSelector) {
        float[] threshold;
        threshold = new float[0];
        float data = Float.parseFloat(ListMap.get(ListMap.size() - 1).get(dataSelector).toString());
        switch (dataSelector) {
            case "pm10":
                threshold = new float[]{0, 30, 80, 150};
                break;
            case "pm1_0":
                threshold = new float[]{0, 15, 35, 75};
                break;
            case "methanen":
                threshold = new float[]{100, 200, 300, 400};
                break;
            case "Co":
                threshold = new float[]{0, 2, 9, 15};
                break;
            case "gas":
                threshold = new float[]{100, 200, 300, 400};
                break;
        }

        if (threshold[0] <= data && data <= threshold[1])
            return 4;
        else if (threshold[1] < data && data <= threshold[2])
            return 3;
        else if (threshold[2] < data && data <= threshold[3])
            return 2;
        else if (threshold[3] < data)
            return 1;
        return 0;
    }

    public void changeImage(ImageView imageView, int grade) {
        if (grade == 4)
            imageView.setImageResource(R.drawable.good);
        else if (grade == 3)
            imageView.setImageResource(R.drawable.botong);
        else if (grade == 2)
            imageView.setImageResource(R.drawable.bad);
        else if (grade == 1)
            imageView.setImageResource(R.drawable.verybad);
    }
}