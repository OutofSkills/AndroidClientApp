package com.intelligentcarmanagement.carmanagementclientapp.api.notifications.responses;

import com.intelligentcarmanagement.carmanagementclientapp.models.User;

public interface IUpdateToken {
    void onFailure(Throwable throwable);

    void onResponse(String newFirebaseToken);
}
