package com.intelligentcarmanagement.carmanagementclientapp.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.intelligentcarmanagement.carmanagementclientapp.api.notifications.responses.IUpdateToken;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.notifications.INotificationsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.notifications.NotificationsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

import java.util.HashMap;

public class PushNotificationService extends FirebaseMessagingService {
    private static final String TAG = "PushNotificationService";
    private NotificationsManager mNotificationManager;
    private SessionManager mSessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = new NotificationsManager(this);
        mSessionManager = new SessionManager(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.d(TAG, "onMessageReceived: called");
        super.onMessageReceived(message);
        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();

        mNotificationManager.displayNotification(title, text);

        super.onMessageReceived(message);
    }
    
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        mSessionManager.addFirebaseToken(token);
    }
}
