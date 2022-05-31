package com.intelligentcarmanagement.carmanagementclientapp.repositories.rides;

import com.intelligentcarmanagement.carmanagementclientapp.api.RetrofitService;
import com.intelligentcarmanagement.carmanagementclientapp.api.rides.IRidesRequests;
import com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses.IGetRidesHistory;
import com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses.IRequestRide;
import com.intelligentcarmanagement.carmanagementclientapp.models.ApiResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RidesRepository implements IRidesRepository{
    @Override
    public void getRides(String token, int userId, IGetRidesHistory getRidesHistory) {
        IRidesRequests ridesRequest = RetrofitService.getRetrofit().create(IRidesRequests.class);
        Call<ArrayList<Ride>> initRequest = ridesRequest.getRides(token, userId);

        initRequest.enqueue(new Callback<ArrayList<Ride>>() {
            @Override
            public void onResponse(Call<ArrayList<Ride>> call, Response<ArrayList<Ride>> response) {
                if(response.isSuccessful())
                    getRidesHistory.onResponse(response.body());
                else {
                    try {
                        getRidesHistory.onFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Ride>> call, Throwable t) {
                getRidesHistory.onFailure(t);
            }
        });
    }

    @Override
    public void requestRide(String token, Ride ride, IRequestRide requestRide) {
        IRidesRequests ridesRequest = RetrofitService.getRetrofit().create(IRidesRequests.class);
        Call<ApiResponse> initRequest = ridesRequest.requestRide(token, ride);

        initRequest.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                requestRide.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                requestRide.onFailure(t);
            }
        });
    }
}
