package com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.Driver;

import java.util.List;

public interface IGetDriver {
    void onResponse(Driver driver);
    void onFailure(Throwable t);
}
