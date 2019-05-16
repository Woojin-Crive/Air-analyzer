package com.danbi.second;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("get_status")
    Call<String> getJson();
}
