package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.adapters.HistoryRecyclerViewAdapter;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityHistoryBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;
import com.intelligentcarmanagement.carmanagementclientapp.viewmodels.HistoryViewModel;

import java.util.ArrayList;

public class HistoryActivity extends DrawerBaseActivity {
    private static final String TAG = "HistoryActivity";
    ActivityHistoryBinding activityHistoryBinding;

    // History recycler view
    private RecyclerView recyclerView;
    private HistoryRecyclerViewAdapter adapter;

    // Circular progress
    private CircularProgressIndicator mProgressIndicator;
    // Refresh layout
    private SwipeRefreshLayout mRefreshLayout;

    private HistoryViewModel mHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHistoryBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(activityHistoryBinding.getRoot());
        allocateActivityTitle("History");

        recyclerView = findViewById(R.id.historyRecyclerView);
        mProgressIndicator = findViewById(R.id.history_progress_indicator);
        mRefreshLayout = findViewById(R.id.history_refresh_layout);

        mHistoryViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        setEventListeners();

        mHistoryViewModel.fetchHistory();
    }

    private void setEventListeners() {
        mHistoryViewModel.getRides().observe(HistoryActivity.this, rides -> initRecyclerView(rides));

        mHistoryViewModel.getProcessingState().observe(HistoryActivity.this, state -> {
            switch (state){
                case ERROR:
                    displayRetryBottomDialog();
                    mProgressIndicator.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    Log.d(TAG, "onChanged: done");
                    mProgressIndicator.setVisibility(View.GONE);
                    break;
                case START:
                    Log.d(TAG, "onChanged: start");
                    mProgressIndicator.setVisibility(View.VISIBLE);
                    mProgressIndicator.bringToFront();
                    break;
            }
        });

        mHistoryViewModel.getRateRequestState().observe(HistoryActivity.this, state -> {
            switch (state)
            {

                case ERROR:
                    mProgressIndicator.setVisibility(View.GONE);
                    displayRetryBottomDialog();
                    break;
                case SUCCESS:
                    mProgressIndicator.setVisibility(View.GONE);
                    Toast.makeText(HistoryActivity.this, "Ride rated successfully.", Toast.LENGTH_SHORT);
                    break;
                case START:
                    mProgressIndicator.setVisibility(View.VISIBLE);
                    mProgressIndicator.bringToFront();
                    break;
            }
        });

        mRefreshLayout.setOnRefreshListener(() -> {
            mRefreshLayout.setRefreshing(false);
            mHistoryViewModel.fetchHistory();
        });
    }

    private void initRecyclerView(ArrayList<Ride> rides)
    {
        adapter = new HistoryRecyclerViewAdapter(this, rides, mHistoryViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displayRetryBottomDialog() {
        final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong.", Snackbar.LENGTH_INDEFINITE);

        snackBar.setAction("Retry", v -> {
            mHistoryViewModel.fetchHistory();
            snackBar.dismiss();
        });
        snackBar.show();
    }
}