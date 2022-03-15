package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends DrawerBaseActivity {
    ActivityProfileBinding activityProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up the drawer
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        allocateActivityTitle("Profile");
    }
}