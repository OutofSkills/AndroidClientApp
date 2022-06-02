package com.intelligentcarmanagement.carmanagementclientapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.utils.ImageConverter;
import com.intelligentcarmanagement.carmanagementclientapp.viewmodels.HistoryViewModel;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Locale;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "HistoryRecyclerViewAdapter";
    Context mContext;
    ArrayList<Ride> mRides = new ArrayList<Ride>();
    private HistoryViewModel viewModel;

    public HistoryRecyclerViewAdapter(Context context, ArrayList<Ride> rides, HistoryViewModel viewModel) {
        this.mRides = rides;
        this.mContext = context;
        this.viewModel = viewModel;
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

        String price = String.valueOf(mRides.get(position).getPrice());
        holder.rideTotalMoney.setText(price);

        // Rating
        if(mRides.get(position).getReview() != null) {
            if(mRides.get(position).getReview().getRating() != 0) {
                holder.ratingBar.setRating((float) mRides.get(position).getReview().getRating());
            }
            else {
                holder.ratingBar.setRating(0);
                holder.ratingBar.setIsIndicator(false);
            }
        }
        else {
            holder.ratingBar.setRating(0);
            holder.ratingBar.setIsIndicator(false);
        }

        // Rating handler
        holder.ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            if(v <= 0) {
                ratingBar.setRating(1);
            }
            else {
                holder.ratingSave.setVisibility(View.VISIBLE);
            }
        });

        holder.ratingSave.setOnClickListener(view -> {
            // TODO: Save rating here
            viewModel.rateRide(mRides.get(holder.getAdapterPosition()).getId(), holder.ratingBar.getRating());
            holder.ratingSave.setVisibility(View.GONE);
        });

        holder.rideTime.setText(String.format("%.2f min", mRides.get(position).getAverageTime()));
        holder.driverUsername.setText(mRides.get(position).getDriver().getEmail());

        // Driver avatar
        String string64 = mRides.get(position).getDriver().getAvatar();
        byte[] imageBytes = ImageConverter.convertBase64ToBytes(string64);
        Bitmap bmp = ImageConverter.convertBytesToBitmap(imageBytes);
        holder.driverAvatar.setImageBitmap(bmp);

        // Driver rating
        holder.driverRating.setText(String.format("%.2f", mRides.get(position).getDriver().getRating()));
    }

    @Override
    public int getItemCount() {
        return mRides.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rideDate, ridePickUpAddress, rideDestinationAddress, rideDistance, rideTime, rideTotalMoney;
        TextView driverUsername, driverRating;
        RatingBar ratingBar;
        ImageView ratingSave;
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
            ratingBar = itemView.findViewById(R.id.history_ride_rating);
            ratingSave = itemView.findViewById(R.id.history_ride_rating_save);
            driverAvatar = itemView.findViewById(R.id.history_ride_driver_avatar);
            driverUsername = itemView.findViewById(R.id.history_ride_driver_username);
            driverRating = itemView.findViewById(R.id.history_ride_driver_rating);
            parentLayout = itemView.findViewById(R.id.historyItemParentLayout);
        }
    }
}
