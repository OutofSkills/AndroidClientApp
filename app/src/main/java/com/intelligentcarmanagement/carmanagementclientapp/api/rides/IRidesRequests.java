package com.intelligentcarmanagement.carmanagementclientapp.api.rides;

import com.intelligentcarmanagement.carmanagementclientapp.models.ApiResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRidesRequests {
    @GET("/api/Rides/client")
    Call<ArrayList<Ride>> getRides(@Header("authorization") String token, @Query("id") int userId);

    @POST("/api/Rides")
    Call<ApiResponse> requestRide(@Header("authorization") String token, @Body Ride ride);
}
