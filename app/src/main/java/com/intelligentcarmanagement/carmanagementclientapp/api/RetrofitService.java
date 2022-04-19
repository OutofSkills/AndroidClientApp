package com.intelligentcarmanagement.carmanagementclientapp.api;

import com.intelligentcarmanagement.carmanagementclientapp.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    public static Retrofit getRetrofit()
    {
        return retrofit;
    }
}
