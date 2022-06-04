package com.intelligentcarmanagement.carmanagementclientapp.api.drivers;

import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IDriversRequests {
    @GET("/api/Drivers")
    Call<List<Driver>> getDrivers(@Header("authorization") String token, @Query("availability") boolean availability);

    @GET("/api/Drivers/byId")
    Call<Driver> getDriver(@Header("authorization") String token, @Query("driverId") int id);
}
