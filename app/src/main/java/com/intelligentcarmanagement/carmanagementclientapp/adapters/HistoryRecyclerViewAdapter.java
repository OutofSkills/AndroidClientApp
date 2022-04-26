package com.intelligentcarmanagement.carmanagementclientapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.models.Ride;

import java.util.ArrayList;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>{
    Context mContext;
    ArrayList<Ride> mRides = new ArrayList<Ride>();

    public HistoryRecyclerViewAdapter(Context mContext, ArrayList<Ride> mRides) {
        this.mRides = mRides;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.historyRideId.setText("#" + String.valueOf(mRides.get(position).getId()));
        holder.historyRideDate.setText(String.format("%1$tY-%1$tm-%1$td", mRides.get(position).getPickUpTime()));
        holder.historyRidePickUpCity.setText(mRides.get(position).getPickUpPlaceName());
        holder.historyRideDestinationCity.setText(mRides.get(position).getDestinationPlaceName());
        holder.historyRideDistance.setText(String.format("%.2f", mRides.get(position).getDistance()) + "km");
    }

    @Override
    public int getItemCount() {
        return mRides.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyRideId, historyRideDate, historyRidePickUpCity, historyRideDestinationCity, historyRideDistance;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyRideId = itemView.findViewById(R.id.historyRideId);
            historyRideDate = itemView.findViewById(R.id.historyRideDate);
            historyRidePickUpCity = itemView.findViewById(R.id.historyRidePickUp);
            historyRideDestinationCity = itemView.findViewById(R.id.historyRideDestination);
            historyRideDistance = itemView.findViewById(R.id.historyRideDistance);
            parentLayout = itemView.findViewById(R.id.historyItemParentLayout);
        }
    }
}
