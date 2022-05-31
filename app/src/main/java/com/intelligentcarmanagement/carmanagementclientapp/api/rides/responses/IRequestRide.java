package com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.ApiResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;

import java.util.ArrayList;

public interface IRequestRide {
    void onResponse(ApiResponse response);
    void onFailure(Throwable t);
}
