package com.intelligentcarmanagement.carmanagementclientapp.api.reviews;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IReviewsRequests {
    @POST("/api/Reviews/ride/rating")
    Call<Void> reviewRide(@Header("authorization") String token, @Query("rideId") int rideId, @Query("rating") double rating);
}
