package com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.intelligentcarmanagement.carmanagementclientapp.api.RetrofitService;
import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.IDriversRequests;
import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses.IGetDriver;
import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses.IGetDrivers;
import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriversRepository implements IDriversRepository{
    @Override
    public void getDrivers(String token, boolean availability, IGetDrivers getResponse) {
        IDriversRequests driversService = RetrofitService.getRetrofit().create(IDriversRequests.class);
        Call<List<Driver>> initRequest = driversService.getDrivers(token, availability);

        initRequest.enqueue(new Callback<List<Driver>>() {

            @Override
            public void onResponse(@NonNull Call<List<Driver>> call, Response<List<Driver>> response) {
                Log.d("Repo", "Body: " + response.body().size());
                if(response.isSuccessful()) {
                    getResponse.onResponse(response.body());
                }
                else
                {
                    getResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                Log.d("Repo", "Exception: " + t.getMessage());
                getResponse.onFailure(t);
            }
        });
    }

    @Override
    public void getDriver(String token, int id, IGetDriver getResponse) {
        IDriversRequests driversService = RetrofitService.getRetrofit().create(IDriversRequests.class);
        Call<Driver> initRequest = driversService.getDriver(token, id);

        initRequest.enqueue(new Callback<Driver>() {

            @Override
            public void onResponse(@NonNull Call<Driver> call, Response<Driver> response) {
                Log.d("Repo", "Body: " + response.body());
                if(response.isSuccessful()) {
                    getResponse.onResponse(response.body());
                }
                else
                {
                    getResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Log.d("Repo", "Exception: " + t.getMessage());
                getResponse.onFailure(t);
            }
        });
    }
}
