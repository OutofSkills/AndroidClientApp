package com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;

import java.util.List;

public interface IGetDrivers {
    void onResponse(List<Driver> driverList);
    void onFailure(Throwable t);
}
