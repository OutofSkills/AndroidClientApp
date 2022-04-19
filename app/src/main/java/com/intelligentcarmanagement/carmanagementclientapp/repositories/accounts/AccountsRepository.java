package com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts;

import android.util.Log;

import com.google.gson.Gson;
import com.intelligentcarmanagement.carmanagementclientapp.api.RetrofitService;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ValidationErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.login.ILoginResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.login.ILoginRequest;
import com.intelligentcarmanagement.carmanagementclientapp.models.Login.LoginRequest;
import com.intelligentcarmanagement.carmanagementclientapp.models.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsRepository implements IAccountsRepository{

    private static final String TAG = "AccountsRepository";

    public void loginRemote(LoginRequest loginRequest, ILoginResponse loginResponse) {
        ILoginRequest loginService = RetrofitService.getRetrofit().create(ILoginRequest.class);
        Call<LoginResponse> initLogin = loginService.login(loginRequest);

        initLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()) {
                    loginResponse.onResponse(response.body());
                }
                else {
                    // TODO: user error codes from HTTP
                    // 400 for validation
                    // 500 for server errors
                    int responseErrorCode = (400 == response.code()) ? 0 :
                            (401 <= response.code() && response.code() < 500) ? 1 :
                                    (500 <= response.code() && response.code() <= 600) ? 2 : 3;

                    try {
                        Gson gson = new Gson();
                        switch (responseErrorCode) {
                            case 0: // Validation error response
                                Log.d(TAG, "Code: " + response.code() + " validation error.");
                                ValidationErrorResponse errorValidationResponse = gson.fromJson(response.errorBody().string(), ValidationErrorResponse.class);
                                loginResponse.onServerValidationFailure(errorValidationResponse);
                                break;
                            case 1:
                                Log.d(TAG, "Code: " + response.code() + " client error.");
                            case 2:
                                Log.d(TAG, "Code: " + response.code() + " server error.");
                                ErrorResponse serverErrorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                                loginResponse.onServerFailure(serverErrorResponse);
                                break;
                            case 3:
                                Log.d(TAG, "Code: " + response.code() + " other error.");
                                loginResponse.onServerFailure(new ErrorResponse("Server error!", "Unknown"));
                                break;
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "Response parse exception: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });
    }
}
