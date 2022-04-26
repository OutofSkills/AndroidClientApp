package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.intelligentcarmanagement.carmanagementclientapp.api.drivers.IGetDrivers;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IGetUser;
import com.intelligentcarmanagement.carmanagementclientapp.models.Driver;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers.DriversRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.drivers.IDriversRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

import java.util.List;

public class AvailableDriversViewModel extends AndroidViewModel {
    private MutableLiveData<List<Driver>> mDriversMutableData = new MutableLiveData<>();

    private IDriversRepository mDriversRepository;
    private SessionManager mSessionManager;

    private String token;

    public AvailableDriversViewModel(@NonNull Application application) {
        super(application);
        mDriversRepository = new DriversRepository();
        mSessionManager = new SessionManager(application);
        token = mSessionManager.getUserData().get(mSessionManager.KEY_TOKEN);
    }

    public void fetchDrivers(boolean availability){
        mDriversRepository.getDrivers("Bearer "+ token, availability, new IGetDrivers() {
            @Override
            public void onResponse(List<Driver> driverList) {
                mDriversMutableData.postValue(driverList);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Profile", "Response user: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<Driver>> getDriversMutableData() {
        return mDriversMutableData;
    }
}
