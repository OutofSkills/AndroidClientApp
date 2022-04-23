package com.intelligentcarmanagement.carmanagementclientapp.api.account;

import com.intelligentcarmanagement.carmanagementclientapp.models.Login.LoginResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IRegisterRequest {
    @POST("/api/ClientsAccount/register")
    Call<RegisterRequest> register(@Body RegisterRequest registerRequest);
}
