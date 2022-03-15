package com.intelligentcarmanagement.carmanagementclientapp.activities;

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
import com.intelligentcarmanagement.carmanagementclientapp.models.Ride;

import java.util.ArrayList;

public class AvailableDriversActivity extends DrawerBaseActivity {

    ActivityAvailableDriversBinding availableDriversBinding;

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
        availableDriversBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        availableDriversFilterButton = findViewById(R.id.driversFilterButton);
        availableDriversFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialogOpen();
            }
        });

        // Get the intent data
        ride = (Ride) getIntent().getSerializableExtra("RideRequest");

        // Setup the recycler view
        recyclerView = findViewById(R.id.availableDriversRecyclerView);
        seedDriversData();
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

    private void initRecyclerView()
    {
        adapter = new AvailableDriversRecyclerViewAdapter(this, mDriversAvatars, mDriversUsernames, mDriversRating, mDriversDistanceAway, ride);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void seedDriversData()
    {
        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic);
        mDriversAvatars.add(btm);
        mDriversUsernames.add("NoobMaster");
        mDriversRating.add("4.7");
        mDriversDistanceAway.add(14);

        Bitmap btm1 = BitmapFactory.decodeResource(getResources(), R.drawable.spongebob);
        mDriversAvatars.add(btm1);
        mDriversUsernames.add("_WeirdGuy_");
        mDriversRating.add("4.8");
        mDriversDistanceAway.add(19);

        initRecyclerView();
    }
}