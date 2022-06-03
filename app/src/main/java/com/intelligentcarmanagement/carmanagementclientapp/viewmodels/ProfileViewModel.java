package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.intelligentcarmanagement.carmanagementclientapp.api.rides.responses.IGetRidesHistory;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IGetUser;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IUpdateUser;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.rides.IRidesRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.rides.RidesRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.users.IUsersRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.users.UsersRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

import java.util.ArrayList;

public class ProfileViewModel extends AndroidViewModel {
    private static final String TAG = "ProfileViewModel";
    private MutableLiveData<User> mUserMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mUpdatingMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mRidesNumberLiveData = new MutableLiveData<>();

    private String token;

    IUsersRepository mUsersRepository;
    IRidesRepository mRidesRepository;
    SessionManager mSessionManager;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mUsersRepository = new UsersRepository();
        mRidesRepository = new RidesRepository();
        mSessionManager = new SessionManager(application);

        token = mSessionManager.getUserData().get(mSessionManager.KEY_JWT_TOKEN);
    }

    public void fetchUser(){
        String email = mSessionManager.getUserData().get(mSessionManager.KEY_EMAIL);

        mUsersRepository.getByEmail("Bearer "+ token, email, new IGetUser() {
            @Override
            public void onResponse(User userResponse) {
                Log.d(TAG, "onResponse: " + userResponse.getRating());
                mUserMutableLiveData.postValue(userResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Profile", "Response user: " + t.getMessage());
            }
        });
    }

    public void fetchRidesNumber() {
        // Get client's id
        String id = mSessionManager.getUserData().get(SessionManager.KEY_ID);
        String jwtToken = mSessionManager.getUserData().get(SessionManager.KEY_JWT_TOKEN);

        // Fetch the rides
        mRidesRepository.getRides("Bearer "+ jwtToken, Integer.valueOf(id), new IGetRidesHistory() {
            @Override
            public void onResponse(ArrayList<Ride> historyRides) {
                int ridesNumber = historyRides.size();
                mRidesNumberLiveData.setValue(ridesNumber);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() );
                t.printStackTrace();
                mRidesNumberLiveData.setValue(0);
            }
        });
    }

    public LiveData<User> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public void updateUserMutableLiveData(User user) {
        mUpdatingMutableLiveData.setValue(true);
        mUsersRepository.update("Bearer "+ token, user.getId(), user, new IUpdateUser() {
            @Override
            public void onResponse(User user) {
                Log.d("ProfileViewModel", "Success update");
                mUserMutableLiveData.setValue(user);
                mSessionManager.addUserAvatar(user.getAvatar());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("ProfileViewModel", "Fail: " + throwable.getMessage());
            }
        });
        mUpdatingMutableLiveData.setValue(false);
    }

    public LiveData<Boolean> getUpdatingMutableLiveData() {
        return mUpdatingMutableLiveData;
    }

    public LiveData<Integer> getRidesNumber()
    {
        return mRidesNumberLiveData;
    }
}
