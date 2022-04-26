package com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers;

import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.IGetDrivers;
import com.intelligentcarmanagement.carmanagementclientapp.models.Driver;

import java.util.List;

public interface IDriversRepository {
    public void getDrivers(String token, boolean availability, IGetDrivers getResponse);
}
