package com.intelligentcarmanagement.carmanagementclientapp.api.account;

import com.intelligentcarmanagement.carmanagementclientapp.models.login.LoginRequest;
import com.intelligentcarmanagement.carmanagementclientapp.models.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILoginRequest {
    @POST("/api/ClientsAccount/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
