package com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;

public interface IGetDriver {
    void onResponse(Driver driver);
    void onFailure(Throwable t);
}
