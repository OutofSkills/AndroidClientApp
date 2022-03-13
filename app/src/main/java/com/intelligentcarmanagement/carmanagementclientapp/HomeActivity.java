package com.intelligentcarmanagement.carmanagementclientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityHomeBinding;

public class HomeActivity extends DrawerBaseActivity {

    ActivityHomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up the drawer
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        allocateActivityTitle("Home");
    }
}