package com.intelligentcarmanagement.carmanagementclientapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences userSession;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_JWT_TOKEN = "jwt_token";
    public static final String KEY_FIREBASE_TOKEN = "firebase_token";

    public SessionManager(Context context)
    {
        this.context = context;
        userSession = context.getSharedPreferences("userSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String id, String email, String token)
    {
        editor.putString(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_JWT_TOKEN, token);

        editor.commit();
    }

    public void addFirebaseToken(String token)
    {
        editor.putString(KEY_FIREBASE_TOKEN, token);

        editor.commit();
    }

    public void addUserAvatar(String avatarBase64)
    {
        editor.putString(KEY_AVATAR, avatarBase64);

        editor.commit();
    }

    public HashMap<String, String> getUserData()
    {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_ID, userSession.getString(KEY_ID, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_JWT_TOKEN, userSession.getString(KEY_JWT_TOKEN, null));
        userData.put(KEY_FIREBASE_TOKEN, userSession.getString(KEY_FIREBASE_TOKEN, null));
        userData.put(KEY_AVATAR, userSession.getString(KEY_AVATAR, null));

        return userData;
    }

    public void clearSession()
    {
        editor.clear();
        editor.commit();
    }

}
