package com.intelligentcarmanagement.carmanagementclientapp.repositories.reviews;

import android.util.Log;

import androidx.annotation.NonNull;

import com.intelligentcarmanagement.carmanagementclientapp.api.RetrofitService;
import com.intelligentcarmanagement.carmanagementclientapp.api.notifications.INotificationsRequests;
import com.intelligentcarmanagement.carmanagementclientapp.api.reviews.IReviewsRequests;
import com.intelligentcarmanagement.carmanagementclientapp.api.reviews.responses.IRateRide;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsRepository implements IReviewsRepository{

    private static final String TAG = "ReviewsRepository";

    @Override
    public void rateRide(String token, int rideId, double rating, IRateRide rateRideResponse) {
        IReviewsRequests reviewsRequests = RetrofitService.getRetrofit().create(IReviewsRequests.class);
        Call<Void> initRequest = reviewsRequests.reviewRide(token, rideId, rating);

        initRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) {
                    rateRideResponse.onResponse();
                }
                else {
                    rateRideResponse.onFailure(
                            new Throwable(response.message())
                    );

                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                rateRideResponse.onFailure(t);
            }
        });
    }
}
