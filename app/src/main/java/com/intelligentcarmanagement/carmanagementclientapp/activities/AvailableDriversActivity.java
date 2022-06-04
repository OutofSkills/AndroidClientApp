package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.L;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.adapters.AvailableDriversRecyclerViewAdapter;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityAvailableDriversBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.utils.DriverDistanceComparator;
import com.intelligentcarmanagement.carmanagementclientapp.utils.DrivingAccuracyComparator;
import com.intelligentcarmanagement.carmanagementclientapp.viewmodels.AvailableDriversViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvailableDriversActivity extends DrawerBaseActivity {
    private static final String TAG = "AvailableDriversActivity";

    // Binding
    ActivityAvailableDriversBinding availableDriversBinding;

    // View model
    private AvailableDriversViewModel mViewModel;

    // Ride request data
    private Ride ride;

    // Buttons
    Button availableDriversBackButton, availableDriversFilterButton;
    // Circular loader
    private CircularProgressIndicator mProgressIndicator;
    // Refresh layout
    private SwipeRefreshLayout mRefreshLayout;

    // Filter
    private int checkedItem = 1;

    // Available drivers recycler view
    RecyclerView recyclerView;
    AvailableDriversRecyclerViewAdapter adapter;

    // Original drivers list
    private List<Driver> originalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        availableDriversBinding = ActivityAvailableDriversBinding.inflate(getLayoutInflater());
        setContentView(availableDriversBinding.getRoot());
        allocateActivityTitle("Available Drivers");

        availableDriversBackButton = findViewById(R.id.availableDriversBackButton);
        availableDriversFilterButton = findViewById(R.id.driversFilterButton);
        mProgressIndicator = findViewById(R.id.available_drivers_progress_indicator);
        mRefreshLayout = findViewById(R.id.available_drivers_refresh_layout);

        // Get the intent data
        ride = (Ride) getIntent().getSerializableExtra("RideRequest");

        if(ride == null)
            Log.d(TAG, "onCreate: Null ride object");

        mViewModel = new ViewModelProvider(this).get(AvailableDriversViewModel.class);

        // Setup the recycler view
        recyclerView = findViewById(R.id.availableDriversRecyclerView);
        mViewModel.fetchDrivers(true);

        // Set event listeners here
        setEventListeners();
    }

    private void FilterDialogOpen()
    {
        String[] filterOptions = {
                "Recommended drivers",
                "Closest drivers"
        };
        String[] checkedById = { filterOptions[checkedItem] };

        new MaterialAlertDialogBuilder(AvailableDriversActivity.this)
                .setTitle("Filter Drivers")
                .setPositiveButton("Filter", (dialogInterface, i) -> {
                    // Handle chosen option
                    switch (checkedById[0]){
                        case "Recommended drivers":
                            List<Driver> recommendedDrivers = sortRecommended();
                            initRecyclerView(recommendedDrivers);
                            break;
                        case "Closest drivers":
                            List<Driver> closestDrivers = sortClosest();
                            initRecyclerView(closestDrivers);
                            break;
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setSingleChoiceItems(filterOptions, checkedItem, (dialogInterface, which) -> {
                    checkedItem = which;
                    checkedById[0] = filterOptions[checkedItem];
                })
                .show();
    }

    private List<Driver> sortClosest() {
        List<Driver> closestList = originalList;
        Collections.sort(closestList, new DriverDistanceComparator(
                new LatLng(
                        Double.parseDouble(ride.getPickUpPlaceLat()),
                        Double.parseDouble(ride.getPickUpPlaceLong())
                )
        ));
        return closestList;
    }

    private List<Driver> sortRecommended() {
        List<Driver> recommendedList = originalList;
        Collections.sort(recommendedList, new DrivingAccuracyComparator());
        return recommendedList;
    }

    private void initRecyclerView(List<Driver> driverList)
    {
        adapter = new AvailableDriversRecyclerViewAdapter(this, driverList, ride);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setEventListeners() {
        mViewModel.getDriversMutableData().observe(AvailableDriversActivity.this, driverList -> {
            originalList = driverList;
            initRecyclerView(driverList);
        });

        mViewModel.getDriversState().observe(AvailableDriversActivity.this, state -> {
            switch (state)
            {

                case ERROR:
                    displayRetryBottomDialog();
                    break;
                case SUCCESS:
                    mProgressIndicator.setVisibility(View.GONE);
                    break;
                case START:
                    mProgressIndicator.setVisibility(View.VISIBLE);
                    mProgressIndicator.bringToFront();
                    break;
            }
        });

        // Back home button action
        availableDriversBackButton.setOnClickListener(view -> finish());

        // Filter drivers button
        availableDriversFilterButton.setOnClickListener(view -> FilterDialogOpen());

        // Refresh the layout on swipe
        mRefreshLayout.setOnRefreshListener(() -> {
            mRefreshLayout.setRefreshing(false);
            mViewModel.fetchDrivers(true);
        });
    }

    private void displayRetryBottomDialog() {
        final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong.", Snackbar.LENGTH_INDEFINITE);

        snackBar.setAction("Retry", v -> {
            mViewModel.fetchDrivers(true);
            snackBar.dismiss();
        });
        snackBar.show();
    }
}