package com.intelligentcarmanagement.carmanagementclientapp.api.users.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.User;

public interface IUpdateUser {
    void onFailure(Throwable throwable);

    void onResponse(User user);
}
