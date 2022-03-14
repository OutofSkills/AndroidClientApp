package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.intelligentcarmanagement.carmanagementclientapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.Theme_CarManagementClientApp);
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        finish();
    }
}