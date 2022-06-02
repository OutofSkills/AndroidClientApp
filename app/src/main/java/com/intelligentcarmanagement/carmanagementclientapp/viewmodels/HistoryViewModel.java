package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.intelligentcarmanagement.carmanagementclientapp.api.reviews.responses.IRateRide;
import com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses.IGetRidesHistory;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.reviews.IReviewsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.reviews.ReviewsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.rides.IRidesRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.rides.RidesRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

import java.util.ArrayList;

public class HistoryViewModel extends AndroidViewModel {
    private static final String TAG = "HistoryViewModel";
    private MutableLiveData<RequestState> mRequestStateLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Ride>> mRidesLiveData = new MutableLiveData<>();

    private MutableLiveData<RequestState> mRateRequestStateLiveData = new MutableLiveData<>();

    private SessionManager mSessionManager;
    private IRidesRepository mRidesRepository;
    private IReviewsRepository mReviewsRepository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        mSessionManager = new SessionManager(application);
        mRidesRepository = new RidesRepository();
        mReviewsRepository = new ReviewsRepository();
    }

    public void fetchHistory() {
        mRequestStateLiveData.setValue(RequestState.START);

        // Get client's id
        String id = mSessionManager.getUserData().get(SessionManager.KEY_ID);
        String jwtToken = mSessionManager.getUserData().get(SessionManager.KEY_JWT_TOKEN);

        // Fetch the rides
        mRidesRepository.getRides(jwtToken, Integer.valueOf(id), new IGetRidesHistory() {
            @Override
            public void onResponse(ArrayList<Ride> historyRides) {
                mRidesLiveData.postValue(historyRides);
                mRequestStateLiveData.setValue(RequestState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() );
                t.printStackTrace();
                mRequestStateLiveData.setValue(RequestState.ERROR);
            }
        });
    }

    public void rateRide(int rideId, double rating){
        mRateRequestStateLiveData.setValue(RequestState.START);

        String jwtToken = mSessionManager.getUserData().get(SessionManager.KEY_JWT_TOKEN);

        mReviewsRepository.rateRide(jwtToken, rideId, rating, new IRateRide() {
            @Override
            public void onResponse() {
                mRateRequestStateLiveData.setValue(RequestState.SUCCESS);
                Log.d(TAG, "onResponse: Success.");
            }

            @Override
            public void onFailure(Throwable t) {
                mRateRequestStateLiveData.setValue(RequestState.ERROR);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Ride>> getRides(){
        return mRidesLiveData;
    }

    public LiveData<RequestState> getProcessingState(){
        return mRequestStateLiveData;
    }

    public LiveData<RequestState> getRateRequestState(){
        return mRateRequestStateLiveData;
    }
}
