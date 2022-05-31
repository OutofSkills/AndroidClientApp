package com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers;

import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses.IGetDriver;
import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses.IGetDrivers;

public interface IDriversRepository {
    public void getDrivers(String token, boolean availability, IGetDrivers getResponse);

    public void getDriver(String token, int id, IGetDriver getResponse);
}
