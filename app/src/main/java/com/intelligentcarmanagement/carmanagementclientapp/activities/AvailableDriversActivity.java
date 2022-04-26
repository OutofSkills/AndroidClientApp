package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.adapters.AvailableDriversRecyclerViewAdapter;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityAvailableDriversBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.Driver;
import com.intelligentcarmanagement.carmanagementclientapp.models.Ride;
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

    // Filter
    private int checkedItem = 1;

    // Available drivers recycler view
    RecyclerView recyclerView;
    AvailableDriversRecyclerViewAdapter adapter;

    // Available drivers data
    ArrayList<Bitmap> mDriversAvatars = new ArrayList<>();
    ArrayList<String> mDriversUsernames = new ArrayList<>();
    ArrayList<String> mDriversRating = new ArrayList<>();
    ArrayList<Integer> mDriversDistanceAway = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        availableDriversBinding = ActivityAvailableDriversBinding.inflate(getLayoutInflater());
        setContentView(availableDriversBinding.getRoot());
        allocateActivityTitle("Available Drivers");

        availableDriversBackButton = findViewById(R.id.availableDriversBackButton);
        availableDriversFilterButton = findViewById(R.id.driversFilterButton);

        // Get the intent data
        ride = (Ride) getIntent().getSerializableExtra("RideRequest");

        if(ride == null)
            Log.d(TAG, "onCreate: Null ride object");

        mViewModel = new ViewModelProvider(this).get(AvailableDriversViewModel.class);

        // Setup the recycler view
        recyclerView = findViewById(R.id.availableDriversRecyclerView);
        seedDriversData();

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

    private void seedDriversData()
    {
        mViewModel.fetchDrivers(true);
        mViewModel.getDriversMutableData().observe(AvailableDriversActivity.this, new Observer<List<Driver>>() {
            @Override
            public void onChanged(List<Driver> driverList) {
                initRecyclerView(driverList);
            }
        });
    }

    private void setEventListeners() {
        // Back home button action
        availableDriversBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Filter drivers button
        availableDriversFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialogOpen();
            }
        });
    }
}