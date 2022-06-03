package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.imageview.ShapeableImageView;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityConfirmRideRequestBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.utils.ImageConverter;
import com.intelligentcarmanagement.carmanagementclientapp.viewmodels.ConfirmRideViewModel;

public class ConfirmRideRequestActivity extends DrawerBaseActivity {

    private static final String TAG = "ConfirmRideRequestActivity";
    ActivityConfirmRideRequestBinding confirmRideRequestBinding;

    private ConfirmRideViewModel mViewModel;

    // Ride request complete data
    private Ride ride;

    // View controls
    private ShapeableImageView driverAvatar;
    TextView driverUsername, driverRating, driverAccuracy;
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
        driverAccuracy = findViewById(R.id.confirm_request_driver_accuracy);
        driverAvatar = findViewById(R.id.confirm_request_driver_avatar);
        driverRating =findViewById(R.id.confirm_request_driver_rating);
        driverUsername = findViewById(R.id.confirm_request_driver_username);
        ridePickUpLocation = findViewById(R.id.confirm_request_pick_up);
        rideDestination = findViewById(R.id.confirm_request_destination);
        rideDistance = findViewById(R.id.confirm_request_distance);
        rideAvgTime = findViewById(R.id.confirm_request_avg_time);
        confirmButton = findViewById(R.id.confirm_ride_request);
        cancelButton = findViewById(R.id.cancel_ride_request);

        mViewModel = new ViewModelProvider(this).get(ConfirmRideViewModel.class);

        // Get the intent data
        ride = (Ride) getIntent().getSerializableExtra("RideRequest");

        // Initialize layout with data
        if(ride != null) {
            Log.d(TAG, "onCreate: " + ride.getDriverId());
            mViewModel.fetchDriver(ride.getDriverId());
            initView();
        }

        // Setup event listeners
        setEventListeners();
    }

    private void initView()
    {
        ridePickUpLocation.setText(ride.getPickUpPlaceName());
        rideDestination.setText(ride.getDestinationPlaceName());
        rideDistance.setText(String.format("%.2f", ride.getDistance()) + " km");
        rideAvgTime.setText(String.format("%.2f", ride.getAverageTime()) + " min");
    }

    private void setEventListeners()
    {
        mViewModel.getDriver().observe(ConfirmRideRequestActivity.this, driver -> {
            driverUsername.setText(driver.getEmail());

            String base64 = driver.getAvatar();
            byte[] imageBytes = ImageConverter.convertBase64ToBytes(base64);
            Bitmap bmp = ImageConverter.convertBytesToBitmap(imageBytes);
            driverAvatar.setImageBitmap(bmp);

            driverRating.setText(String.format("%.2f", driver.getRating()));
            driverAccuracy.setText(String.format("%.2f", driver.getAccuracy()) + '%');
        });

        confirmButton.setOnClickListener(view -> {
            mViewModel.requestRide(ride);
        });

        mViewModel.getRideRequestState().observe(ConfirmRideRequestActivity.this, state -> {
            switch (state)
            {

                case ERROR:
                    Toast.makeText(ConfirmRideRequestActivity.this, mViewModel.getMessage().getValue(), Toast.LENGTH_SHORT);
                    break;
                case SUCCESS:
                    Intent intent = new Intent(ConfirmRideRequestActivity.this, SuccessActivity.class);
                    intent.putExtra("message", mViewModel.getMessage().getValue());
                    startActivity(intent);
                    finish();
                    break;
                case START:
                    break;
            }
        });

        cancelButton.setOnClickListener(view -> finish());
    }
}