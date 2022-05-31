package com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;

import java.util.ArrayList;

public interface IGetRidesHistory {
    void onResponse(ArrayList<Ride> historyRides);
    void onFailure(Throwable t);
}
