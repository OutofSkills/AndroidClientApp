package com.intelligentcarmanagement.carmanagementclientapp.repositories.notifications;

import android.util.Log;

import androidx.annotation.NonNull;

import com.intelligentcarmanagement.carmanagementclientapp.api.RetrofitService;
import com.intelligentcarmanagement.carmanagementclientapp.api.notifications.INotificationsRequests;
import com.intelligentcarmanagement.carmanagementclientapp.api.notifications.responses.IUpdateToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsRepository implements INotificationsRepository {

    @Override
    public void updateToken(String token, String id, String firebaseToken, IUpdateToken updateTokenResponse) {
        INotificationsRequests notificationsRequests = RetrofitService.getRetrofit().create(INotificationsRequests.class);
        Call<String> initRequest = notificationsRequests.updateToken(token, id, firebaseToken);

        initRequest.enqueue(new Callback<String>() {

            @Override
            public void onResponse(@NonNull Call<String> call, Response<String> response) {
                Log.d("Repo", "Body: " + response.body());
                if(response.isSuccessful()) {
                    Log.d("Repo", "Body: " + response.body());
                    updateTokenResponse.onResponse(response.body());
                }
                else
                {
                    updateTokenResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Repo", "Exception: " + t.getMessage());
                updateTokenResponse.onFailure(t);
            }
        });
    }
}
