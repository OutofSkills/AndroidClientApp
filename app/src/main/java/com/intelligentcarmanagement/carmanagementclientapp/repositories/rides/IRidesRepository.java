package com.intelligentcarmanagement.carmanagementclientapp.repositories.rides;

import com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses.IGetRidesHistory;
import com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses.IRequestRide;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IGetUser;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;

public interface IRidesRepository {
    public void getRides(String token, int userId, IGetRidesHistory getRidesHistory);

    public void requestRide(String token, Ride ride, IRequestRide requestRide);
}
