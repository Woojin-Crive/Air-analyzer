package com.danbi.second;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    List<Map> ListMap = new ArrayList<>();
    String data = "";
    JSONArray jsonArray = new JSONArray();
    String sfName = "myFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sf = getSharedPreferences(sfName, 0);
        data = sf.getString("name", ""); // 키값으로 꺼냄
        if (!data.equals("")) {
            try {
                jsonArray = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, String> map = new HashMap<>();
                Gson gson = new Gson();
                try {
                    ListMap.add(gson.fromJson(jsonArray.getString(i), map.getClass()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Map<String, Float> discomfort = new HashMap<>();
                float discomfortData = (9.0f / 5 * Float.parseFloat(ListMap.get(i).get("Temp").toString())) - 0.55f * (1f - Float.parseFloat(ListMap.get(i).get("Humid").toString()) / 100f) * (9.0f / 5.0f * Float.parseFloat(ListMap.get(i).get("Temp").toString()) - 26.0f) + 32.0f;
                discomfort.put("Discomfort", discomfortData);
                ListMap.get(i).putAll(discomfort);
            }
            Log.d("discomfort data", ListMap.toString());
        } else {
            Toast.makeText(getApplicationContext(), "데이터를 업데이트 해주세요.", Toast.LENGTH_LONG).show();
        }
        loadFragment(new OneFragment());
        //fadeOutAndHideImage(loadingImage);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

//    private void fadeOutAndHideImage(final ImageView img) {
//        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setInterpolator(new AccelerateInterpolator());
//        fadeOut.setDuration(1000);
//
//        fadeOut.setAnimationListener(new Animation.AnimationListener() {
//            public void onAnimationEnd(Animation animation) {
//                img.setVisibility(View.GONE);
//            }
//
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            public void onAnimationStart(Animation animation) {
//            }
//        });
//
//        img.startAnimation(fadeOut);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_one:
                fragment = new OneFragment();
                break;

            case R.id.navigation_two:
                fragment = new TwoFragment();
                break;

            case R.id.navigation_three:
                fragment = new ThreeFragment();
                break;
//
//            case R.id.navigation_four:
//                fragment = new FourFragment();
//                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}