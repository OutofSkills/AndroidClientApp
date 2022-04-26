package com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.intelligentcarmanagement.carmanagementclientapp.api.RetrofitService;
import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.IDriversRequests;
import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.IGetDrivers;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.IUsersRequests;
import com.intelligentcarmanagement.carmanagementclientapp.models.Driver;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;

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
}
