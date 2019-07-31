package com.danbi.second;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("repos")
    Call<String> getJson();
}
