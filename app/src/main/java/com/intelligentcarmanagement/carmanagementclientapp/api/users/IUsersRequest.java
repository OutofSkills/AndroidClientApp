package com.intelligentcarmanagement.carmanagementclientapp.api.users;

import com.intelligentcarmanagement.carmanagementclientapp.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IUsersRequest {
    @GET("/api/Clients/byEmail")
    Call<User> getUserByEmail(@Header("authorization") String token, @Query("email") String email);

    @PUT("/api/Clients")
    Call<User> updateUser(@Header("authorization") String token, @Query("id") int id, @Body User user);
}
