package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.intelligentcarmanagement.carmanagementclientapp.services.JwtTokenService;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

import java.util.Map;

public class AuthViewModel extends AndroidViewModel {
    private SessionManager sessionManager;
    private Map<Object, Object> claims;

    public AuthViewModel(Application context) {
        super(context);
        sessionManager = new SessionManager(context);
    }

    // Return true if the user token is valid
    // and false if it is null or expired
    public boolean IsAuthenticated() {
        String token = sessionManager.getUserData().get(sessionManager.KEY_JWT_TOKEN);
        if (token == null) {
            Log.d("AuthViewModel", "Token not found.");
            return false;
        }

        try {
            claims = new JwtTokenService().decodePayloadClaims(token);

            Log.d("AuthViewModel", "Claims: " + claims.values());

            // If the token is expired
            // remove it and redirect the user to the login screen
            if(JwtTokenService.isTokenExpired(Long.parseLong(String.valueOf(claims.get("exp"))))) {
                Log.d("AuthViewModel", "Token expired.");
                sessionManager.clearSession();
                return false;
            }

            return true;
        } catch (Exception e) {
            Log.d("AuthViewModel", "Claims decoding error: " + e.getMessage());
        }

        // Shouldn't reach here
        return false;
    }
}