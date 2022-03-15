package com.intelligentcarmanagement.carmanagementclientapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.activities.AvailableDriversActivity;
import com.intelligentcarmanagement.carmanagementclientapp.activities.ConfirmRideRequestActivity;
import com.intelligentcarmanagement.carmanagementclientapp.activities.HomeActivity;
import com.intelligentcarmanagement.carmanagementclientapp.models.Ride;

import java.util.ArrayList;

public class AvailableDriversRecyclerViewAdapter extends RecyclerView.Adapter<AvailableDriversRecyclerViewAdapter.ViewHolder>{
    Context mContext;
    ArrayList<Bitmap> mDriversAvatars = new ArrayList<>();
    ArrayList<String> mDriversUsernames = new ArrayList<>();
    ArrayList<String> mDriversRating = new ArrayList<>();
    ArrayList<Integer> mDriversDistanceAway = new ArrayList<>();

    // Ride partial data
    Ride mRide;

    public AvailableDriversRecyclerViewAdapter(Context mContext, ArrayList<Bitmap> mDriversAvatars, ArrayList<String> mDriversUsernames, ArrayList<String> mDriversRating, ArrayList<Integer> mDriversDistanceAway, Ride ride) {
        this.mDriversAvatars = mDriversAvatars;
        this.mDriversUsernames = mDriversUsernames;
        this.mDriversRating = mDriversRating;
        this.mDriversDistanceAway = mDriversDistanceAway;
        this.mContext = mContext;
        mRide = ride;
    }

    @NonNull
    @Override
    public AvailableDriversRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_driver_list_item, parent, false);
        AvailableDriversRecyclerViewAdapter.ViewHolder holder = new AvailableDriversRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.driverAvatar.setImageBitmap(mDriversAvatars.get(position));
        holder.driverUsername.setText(mDriversUsernames.get(position));
        holder.driverRating.setText(mDriversRating.get(position));
        holder.driverDistanceAway.setText(mDriversDistanceAway.get(position).toString() + "km away");

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ConfirmRideRequestActivity.class);
                // On click on a driver layout
                // navigate to request confirmation view and
                // transmit the complete ride data
                intent.putExtra("RideRequest", mRide);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDriversUsernames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView driverAvatar;
        TextView driverUsername, driverRating, driverDistanceAway;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            driverAvatar = itemView.findViewById(R.id.driver_item_image);
            driverUsername = itemView.findViewById(R.id.driver_item_username);
            driverRating = itemView.findViewById(R.id.driver_item_rating);
            driverDistanceAway = itemView.findViewById(R.id.driver_item_distance);
            parentLayout = itemView.findViewById(R.id.driverItemParentLayout);
        }
    }
}
