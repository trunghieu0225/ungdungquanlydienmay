package com.example.dienmayapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    private static final String BASE_URL =
            "http://10.0.2.2:8080/Server_API_DienMay-1.0-SNAPSHOT/api/";

    public static Retrofit getClient() {

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}