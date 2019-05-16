package com.danbi.second;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetRetrofit {
    private static NetRetrofit ourInstance = new NetRetrofit();

    public static NetRetrofit getInstance() {
        return ourInstance;
    }

    private NetRetrofit() {
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://13.114.94.147:8080/")
            //.addConverterFactory(GsonConverterFactory.create()) // 파싱등록
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    RetrofitService service = retrofit.create(RetrofitService.class);

    public RetrofitService getService() {
        return service;
    }
}
