package com.intelligentcarmanagement.carmanagementclientapp.api.notifications;

import com.intelligentcarmanagement.carmanagementclientapp.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface INotificationsRequests {
    @PUT("/api/Notifications/token")
    @Headers("Accept: application/json")
    Call<String> updateToken(@Header("authorization") String token, @Query("id") String id, @Body String firebaseToken);
}
