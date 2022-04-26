package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityConfirmRideRequestBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.services.NotificationsService;

public class ConfirmRideRequestActivity extends DrawerBaseActivity {

    ActivityConfirmRideRequestBinding confirmRideRequestBinding;

    private NotificationsService mNotificationService;
    // Ride request complete data
    private Ride ride;

    // View controls
    TextView ridePickUpLocation, rideDestination, rideDistance, rideAvgTime;
    Button confirmButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up the drawer
        confirmRideRequestBinding = ActivityConfirmRideRequestBinding.inflate(getLayoutInflater());
        setContentView(confirmRideRequestBinding.getRoot());
        allocateActivityTitle("Confirm Request");

        // Bind controls to views
        ridePickUpLocation = findViewById(R.id.confirm_request_pick_up);
        rideDestination = findViewById(R.id.confirm_request_destination);
        rideDistance = findViewById(R.id.confirm_request_distance);
        rideAvgTime = findViewById(R.id.confirm_request_avg_time);
        confirmButton = findViewById(R.id.confirm_ride_request);
        cancelButton = findViewById(R.id.cancel_ride_request);

        // Get the intent data
        ride = (Ride) getIntent().getSerializableExtra("RideRequest");

        // Initialize layout with data
        initView();

        // Setup event listeners
        setEventListeners();
    }

    private void initView()
    {
        ridePickUpLocation.setText(ride.getPickUpPlaceName());
        rideDestination.setText(ride.getDestinationPlaceName());
        rideDistance.setText(String.valueOf(ride.getDistance()) + " km");
        rideAvgTime.setText(String.valueOf(ride.getAverageTime()) + " min");
    }

    private void setEventListeners()
    {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: handle confirm ride request
                mNotificationService = new NotificationsService(ConfirmRideRequestActivity.this);
                mNotificationService.displayNotification("Driver Confirmation", "Driver " + "Johnny" +" confirmed your request.");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}