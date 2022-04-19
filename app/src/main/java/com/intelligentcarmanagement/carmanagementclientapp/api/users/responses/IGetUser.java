package com.intelligentcarmanagement.carmanagementclientapp.api.users.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.User;

public interface IGetUser {
    void onResponse(User userResponse);
    void onFailure(Throwable t);
}
