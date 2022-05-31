package com.intelligentcarmanagement.carmanagementclientapp.adapters;

import android.content.Context;
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
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.utils.ImageConverter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Locale;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "HistoryRecyclerViewAdapter";
    Context mContext;
    ArrayList<Ride> mRides = new ArrayList<Ride>();

    public HistoryRecyclerViewAdapter(Context context, ArrayList<Ride> rides) {
        this.mRides = rides;
        this.mContext = context;
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
        PrettyTime p = new PrettyTime();
        p.setLocale(Locale.ENGLISH);
        holder.rideDate.setText(p.format(mRides.get(position).getPickUpTime()));

        holder.ridePickUpAddress.setText(mRides.get(position).getPickUpPlaceName());
        holder.rideDestinationAddress.setText(mRides.get(position).getDestinationPlaceName());
        holder.rideDistance.setText(String.format("%.2f", mRides.get(position).getDistance()) + "km");
        // TODO: money
        holder.rideTotalMoney.setText("10$");

        holder.rideTime.setText(String.format("%.2f", mRides.get(position).getAverageTime()) + " min");
        holder.driverUsername.setText(mRides.get(position).getDriver().getEmail());

        String string64 = mRides.get(position).getDriver().getAvatar();
        byte[] imageBytes = ImageConverter.convertBase64ToBytes(string64);
        Bitmap bmp = ImageConverter.convertBytesToBitmap(imageBytes);
        holder.driverAvatar.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return mRides.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rideDate, ridePickUpAddress, rideDestinationAddress, rideDistance, rideTime, rideTotalMoney;
        TextView driverUsername, driverRating;
        ShapeableImageView driverAvatar;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rideDate = itemView.findViewById(R.id.history_ride_date);
            ridePickUpAddress = itemView.findViewById(R.id.history_ride_from);
            rideDestinationAddress = itemView.findViewById(R.id.history_ride_to);
            rideDistance = itemView.findViewById(R.id.history_ride_total_distance);
            rideTime = itemView.findViewById(R.id.history_ride_total_time);
            rideTotalMoney = itemView.findViewById(R.id.history_ride_total_money);
            driverAvatar = itemView.findViewById(R.id.history_ride_driver_avatar);
            driverUsername = itemView.findViewById(R.id.history_ride_driver_username);
            driverRating = itemView.findViewById(R.id.history_ride_driver_rating);
            parentLayout = itemView.findViewById(R.id.historyItemParentLayout);
        }
    }
}
