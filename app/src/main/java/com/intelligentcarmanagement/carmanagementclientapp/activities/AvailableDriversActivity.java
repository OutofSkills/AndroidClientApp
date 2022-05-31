package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.adapters.AvailableDriversRecyclerViewAdapter;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityAvailableDriversBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.Driver;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;
import com.intelligentcarmanagement.carmanagementclientapp.viewmodels.AvailableDriversViewModel;

import java.util.ArrayList;
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
                .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setSingleChoiceItems(filterOptions, checkedItem, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        checkedItem = which;
                        checkedById[0] = filterOptions[checkedItem];
                    }
                })
                .show();
    }

    private void initRecyclerView(List<Driver> driverList)
    {
        adapter = new AvailableDriversRecyclerViewAdapter(this, driverList, ride);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setEventListeners() {
        mViewModel.getDriversMutableData().observe(AvailableDriversActivity.this, driverList -> initRecyclerView(driverList));

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