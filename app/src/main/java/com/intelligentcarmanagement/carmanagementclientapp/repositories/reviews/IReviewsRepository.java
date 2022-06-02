package com.intelligentcarmanagement.carmanagementclientapp.repositories.reviews;

import com.intelligentcarmanagement.carmanagementclientapp.api.reviews.responses.IRateRide;

public interface IReviewsRepository {
    public void rateRide(String token, int rideId, double rating, IRateRide rateRideResponse);
}
