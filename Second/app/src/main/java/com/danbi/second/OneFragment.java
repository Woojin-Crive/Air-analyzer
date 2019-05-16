package com.danbi.second;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OneFragment extends Fragment {
    @Nullable
    List<Map> ListMap = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        ListMap = ((MainActivity) getActivity()).ListMap;
        //Log.d("data received", ListMap.toString());
        ImageView imageView1 = view.findViewById(R.id.imageViewGood);
        ImageView imageView2 = view.findViewById(R.id.imageViewBotong);
        ImageView imageView3 = view.findViewById(R.id.imageViewBad);
        ImageView imageView4 = view.findViewById(R.id.imageViewVeryBad);
        ImageView imageView5 = view.findViewById(R.id.imageViewGood1);
        ImageView imageView6 = view.findViewById(R.id.imageViewBotong1);
        ImageView imageView7 = view.findViewById(R.id.imageViewBad1);
        ImageView imageView8 = view.findViewById(R.id.imageViewVeryBad1);
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
        imageView6.setVisibility(View.INVISIBLE);
        imageView7.setVisibility(View.INVISIBLE);
        imageView8.setVisibility(View.INVISIBLE);
        int pm10Case = 0;
        int pm1_0Case = 0;
        float pm10Data = Float.parseFloat(ListMap.get(0).get("pm10").toString());

        if (pm10Data >= 0 && pm10Data <= 30)
            pm10Case = 1;
        else if (pm10Data >= 31 && pm10Data <= 80)
            pm10Case = 2;
        else if (pm10Data >= 81 && pm10Data <= 150)
            pm10Case = 3;
        else if (pm10Data >= 151)
            pm10Case = 4;

        float pm1_0Data = Float.parseFloat(ListMap.get(0).get("pm1_0").toString());
        if (pm1_0Data >= 0 && pm1_0Data <= 15)
            pm1_0Case = 1;
        else if (pm1_0Data >= 16 && pm1_0Data <= 35)
            pm1_0Case = 2;
        else if (pm1_0Data >= 36 && pm1_0Data <= 75)
            pm1_0Case = 3;
        else if (pm1_0Data >= 76)
            pm1_0Case = 4;

        if (pm10Case == 1)
            imageView1.setVisibility(View.VISIBLE);

        else if (pm10Case == 2)
            imageView2.setVisibility(View.VISIBLE);

        else if (pm10Case == 3)
            imageView3.setVisibility(View.VISIBLE);

        else if (pm10Case == 4)
            imageView4.setVisibility(View.VISIBLE);

        if (pm1_0Case == 1)
            imageView5.setVisibility(View.VISIBLE);

        else if (pm1_0Case == 2)
            imageView6.setVisibility(View.VISIBLE);

        else if (pm1_0Case == 3)
            imageView7.setVisibility(View.VISIBLE);

        else if (pm1_0Case == 4)
            imageView8.setVisibility(View.VISIBLE);

        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.weather.go.kr/weather/main.jsp";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        TextView textView2 = view.findViewById(R.id.textView2);
        textView2.setText(DateFormat.format("HH:mm", System.currentTimeMillis()));
        return view;
    }
}