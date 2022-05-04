package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.api.account.IRegisterResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ValidationErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.RegisterRequest;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts.AccountsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts.IAccountsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class RegisterViewModel extends AndroidViewModel {
    private static final String TAG = "RegisterViewModel";

    private MutableLiveData<RequestState> mRegisterStateMutableData = new MutableLiveData<>();
    MutableLiveData<String[]> mErrorsMutableLiveData = new MutableLiveData<>();

    private IAccountsRepository mAccountsRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        mAccountsRepository = new AccountsRepository();
    }

    public void register(RegisterRequest registerRequest)
    {
        mRegisterStateMutableData.setValue(RequestState.START);
        Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());

        // Get the device's notifications token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        registerRequest.setNotificationsToken(task.getResult());

                        sendRegisterRequest(registerRequest);
                    }
                });
    }

    private void sendRegisterRequest(RegisterRequest registerRequest)
    {
        // Send the request
        mAccountsRepository.register(registerRequest, new IRegisterResponse() {
            @Override
            public void onResponse(RegisterRequest request) {
                mRegisterStateMutableData.setValue(RequestState.SUCCESS);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                mErrorsMutableLiveData.postValue(new String[]{t.getMessage()});
                mRegisterStateMutableData.setValue(RequestState.ERROR);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }

            @Override
            public void onServerValidationFailure(ValidationErrorResponse errorValidationResponse) {
                List<String> validationErrors = new ArrayList<>();
                for (String[] array: errorValidationResponse.getErrors().values()) {
                    for (String string: array) {
                        validationErrors.add(string);
                    }
                }
                mErrorsMutableLiveData.postValue(validationErrors.toArray(new String[0]));
                mRegisterStateMutableData.setValue(RequestState.ERROR);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }

            @Override
            public void onServerFailure(ErrorResponse serverErrorResponse) {
                mErrorsMutableLiveData.postValue(new String[]{serverErrorResponse.getMessage()});
                mRegisterStateMutableData.setValue(RequestState.ERROR);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }
        });
    }

    public LiveData<RequestState> getRegisterState() {
        return mRegisterStateMutableData;
    }

    public LiveData<String[]> getErrorsMutableLiveData(){
        return mErrorsMutableLiveData;
    }
}
