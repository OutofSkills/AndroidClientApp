package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.intelligentcarmanagement.carmanagementclientapp.api.account.IRegisterResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ValidationErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.RegisterRequest;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts.AccountsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts.IAccountsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;

public class RegisterViewModel extends AndroidViewModel {
    private static final String TAG = "RegisterViewModel";

    private MutableLiveData<RequestState> mRegisterStateMutableData = new MutableLiveData<>();
    private MutableLiveData<String> mRegisterErrorMutableData = new MutableLiveData<>();

    private IAccountsRepository mAccountsRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        mAccountsRepository = new AccountsRepository();
    }

    public void register(RegisterRequest registerRequest)
    {
        mRegisterStateMutableData.setValue(RequestState.START);
        Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());

        mAccountsRepository.register(registerRequest, new IRegisterResponse() {
            @Override
            public void onResponse(RegisterRequest request) {
                mRegisterStateMutableData.setValue(RequestState.SUCCESS);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                mRegisterErrorMutableData.postValue(t.toString());
                mRegisterStateMutableData.setValue(RequestState.ERROR);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }

            @Override
            public void onServerValidationFailure(ValidationErrorResponse errorValidationResponse) {
                mRegisterErrorMutableData.postValue(errorValidationResponse.getErrors().toString());
                mRegisterStateMutableData.setValue(RequestState.ERROR);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }

            @Override
            public void onServerFailure(ErrorResponse serverErrorResponse) {
                mRegisterErrorMutableData.postValue(serverErrorResponse.getMessage());
                mRegisterStateMutableData.setValue(RequestState.ERROR);
                Log.d("ViewModel", "Register State: " + mRegisterStateMutableData.getValue());
            }
        });
    }

    public LiveData<RequestState> getRegisterState() {
        return mRegisterStateMutableData;
    }

    public LiveData<String> getRegisterError(){
        return mRegisterErrorMutableData;
    }
}
