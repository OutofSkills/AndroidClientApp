package com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.Driver;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;

import java.util.List;

public interface IGetDrivers {
    void onResponse(List<Driver> driverList);
    void onFailure(Throwable t);
}
