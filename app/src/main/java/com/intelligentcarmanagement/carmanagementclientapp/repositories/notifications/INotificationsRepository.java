package com.intelligentcarmanagement.carmanagementclientapp.repositories.notifications;

import com.intelligentcarmanagement.carmanagementclientapp.api.notifications.responses.IUpdateToken;

public interface INotificationsRepository {
    public void updateToken(String token, String id, String firebaseToken, IUpdateToken updateTokenResponse);
}
