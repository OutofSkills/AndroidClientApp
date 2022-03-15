package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.adapters.HistoryRecyclerViewAdapter;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityHistoryBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.Ride;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HistoryActivity extends DrawerBaseActivity {
    ActivityHistoryBinding activityHistoryBinding;

    // History recycler view
    RecyclerView recyclerView;
    HistoryRecyclerViewAdapter adapter;

    // History rides
    ArrayList<Ride> historyRides = new ArrayList<Ride>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHistoryBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(activityHistoryBinding.getRoot());
        allocateActivityTitle("History");

        recyclerView = findViewById(R.id.historyRecyclerView);

        seedHistoryData();
    }

    private void initRecyclerView()
    {
        adapter = new HistoryRecyclerViewAdapter(this, historyRides);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void seedHistoryData()
    {
        historyRides.add(new Ride(1, 0, "", "Craiova", "", "Bucuresti", "23.94", "23.98", "23.94", "23.98"
                , 24.54, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime()));

        historyRides.add(new Ride(1, 0, "", "Craiova", "", "Bucuresti", "23.94", "23.98", "23.94", "23.98"
                , 24.54, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime()));

        historyRides.add(new Ride(1, 0, "", "Craiova", "", "Bucuresti", "23.94", "23.98", "23.94", "23.98"
                , 24.54, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime()));

        initRecyclerView();
    }
}