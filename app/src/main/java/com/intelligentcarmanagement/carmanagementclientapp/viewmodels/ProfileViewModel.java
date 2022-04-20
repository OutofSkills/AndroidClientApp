package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IGetUser;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IUpdateUser;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.users.IUsersRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.users.UsersRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

public class ProfileViewModel extends AndroidViewModel {
    private MutableLiveData<User> mUserMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mUpdatingMutableLiveData = new MutableLiveData<>();
    private String token;

    IUsersRepository mUsersRepository;
    SessionManager sessionManager;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mUsersRepository = new UsersRepository();
        sessionManager = new SessionManager(application);

        token = sessionManager.getUserData().get(sessionManager.KEY_TOKEN);
    }

    public void fetchUser(){
        String email = sessionManager.getUserData().get(sessionManager.KEY_EMAIL);

        mUsersRepository.getByEmail("Bearer "+ token, email, new IGetUser() {
            @Override
            public void onResponse(User userResponse) {
                mUserMutableLiveData.postValue(userResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Profile", "Response user: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public void updateUserMutableLiveData(User user) {
        mUpdatingMutableLiveData.setValue(true);
        mUsersRepository.update("Bearer "+ token, user.getId(), user, new IUpdateUser() {
            @Override
            public void onResponse(User user) {
                Log.d("ProfileViewModel", "Success update");
                mUserMutableLiveData.setValue(user);
                sessionManager.addUserAvatar(user.getAvatar());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("ProfileViewModel", "Fail: " + throwable.getMessage());
            }
        });
        mUpdatingMutableLiveData.setValue(false);
    }

    public MutableLiveData<Boolean> getUpdatingMutableLiveData() {
        return mUpdatingMutableLiveData;
    }
}
