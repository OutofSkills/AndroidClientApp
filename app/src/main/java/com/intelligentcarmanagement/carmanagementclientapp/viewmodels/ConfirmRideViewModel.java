package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.responses.IGetDriver;
import com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses.IRequestRide;
import com.intelligentcarmanagement.carmanagementclientapp.models.ApiResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers.DriversRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers.IDriversRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.rides.IRidesRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.rides.RidesRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

public class ConfirmRideViewModel extends AndroidViewModel {
    private static final String TAG = "ConfirmRideViewModel";
    private MutableLiveData<Driver> mDriverLiveData = new MutableLiveData<>();
    private MutableLiveData<RequestState> mDriverFetchStateLiveData = new MutableLiveData<>();

    private MutableLiveData<RequestState> mRideRequestStateLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mRideRequestMessageLiveData = new MutableLiveData<>();

    private IDriversRepository mDriversRepository;
    private IRidesRepository mRidesRepository;
    private SessionManager mSessionManager;

    public ConfirmRideViewModel(@NonNull Application application) {
        super(application);
        mSessionManager = new SessionManager(application);
        mDriversRepository = new DriversRepository();
        mRidesRepository = new RidesRepository();
    }

    public void fetchDriver(int id)
    {
        mDriverFetchStateLiveData.setValue(RequestState.START);
        String token = mSessionManager.getUserData().get(SessionManager.KEY_JWT_TOKEN);

        mDriversRepository.getDriver("Bearer "+ token, id, new IGetDriver() {
            @Override
            public void onResponse(Driver driver) {
                mDriverLiveData.postValue(driver);
                mDriverFetchStateLiveData.setValue(RequestState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                mDriverFetchStateLiveData.setValue(RequestState.ERROR);
                Log.d("Profile", "Response user: " + t.getMessage());
            }
        });
    }

    public void requestRide(Ride ride)
    {
        mRideRequestStateLiveData.setValue(RequestState.START);
        String token = mSessionManager.getUserData().get(SessionManager.KEY_JWT_TOKEN);
        String userId = mSessionManager.getUserData().get(SessionManager.KEY_ID);

        ride.setClientId(Integer.parseInt(userId));

        mRidesRepository.requestRide("Bearer " + token, ride, new IRequestRide() {
            @Override
            public void onResponse(ApiResponse response) {
                if(response.isSuccess())
                {
                    mRideRequestMessageLiveData.setValue(response.getMessage());
                    mRideRequestStateLiveData.setValue(RequestState.SUCCESS);
                    Log.d(TAG, "onResponse: " + response.getMessage());
                }
                else {
                    mRideRequestMessageLiveData.setValue(response.getMessage());
                    mRideRequestStateLiveData.setValue(RequestState.ERROR);
                    Log.d(TAG, "onResponse: " + response.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mRideRequestMessageLiveData.setValue(t.getMessage());
                mRideRequestStateLiveData.setValue(RequestState.ERROR);
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

    public LiveData<Driver> getDriver() {
        return mDriverLiveData;
    }

    public LiveData<RequestState> getRideRequestState() {
        return mRideRequestStateLiveData;
    }

    public LiveData<String> getMessage()
    {
        return mRideRequestMessageLiveData;
    }
}
