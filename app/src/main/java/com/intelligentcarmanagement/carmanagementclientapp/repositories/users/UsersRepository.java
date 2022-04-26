package com.intelligentcarmanagement.carmanagementclientapp.repositories.users;

import android.util.Log;

import androidx.annotation.NonNull;

import com.intelligentcarmanagement.carmanagementclientapp.api.RetrofitService;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.IUsersRequests;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IGetUser;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IUpdateUser;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository implements IUsersRepository{

    private static final String TAG = "UsersRepository";

    public void getByEmail(String token, String email, IGetUser getUserResponse) {
        IUsersRequests usersService = RetrofitService.getRetrofit().create(IUsersRequests.class);
        Call<User> initRequest = usersService.getUserByEmail(token, email);

        initRequest.enqueue(new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, Response<User> response) {
                Log.d("Repo", "Body: " + response.body());
                if(response.isSuccessful()) {
                    Log.d("Repo", "Body: " + response.body());
                    getUserResponse.onResponse(response.body());
                }
                else
                {
                    getUserResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Repo", "Exception: " + t.getMessage());
                getUserResponse.onFailure(t);
            }
        });
    }

    public void update(String token, int id, User user, IUpdateUser updateUserResponse)
    {
        IUsersRequests usersService = RetrofitService.getRetrofit().create(IUsersRequests.class);
        Call<User> initRequest = usersService.updateUser(token, id, user);

        initRequest.enqueue(new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, Response<User> response) {
                Log.d("Repo", "Body: " + response.body());
                if(response.isSuccessful()) {
                    Log.d("Repo", "Body: " + response.body());
                    updateUserResponse.onResponse(response.body());
                }
                else
                {
                    updateUserResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Repo", "Exception: " + t.getMessage());
                updateUserResponse.onFailure(t);
            }
        });
    }
}
