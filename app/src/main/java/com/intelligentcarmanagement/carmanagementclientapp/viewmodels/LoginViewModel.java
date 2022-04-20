package com.intelligentcarmanagement.carmanagementclientapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ValidationErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.login.ILoginResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IGetUser;
import com.intelligentcarmanagement.carmanagementclientapp.models.Login.LoginRequest;
import com.intelligentcarmanagement.carmanagementclientapp.models.Login.LoginResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts.AccountsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts.IAccountsRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.users.IUsersRepository;
import com.intelligentcarmanagement.carmanagementclientapp.repositories.users.UsersRepository;
import com.intelligentcarmanagement.carmanagementclientapp.utils.JwtParser;
import com.intelligentcarmanagement.carmanagementclientapp.utils.LoginState;
import com.intelligentcarmanagement.carmanagementclientapp.utils.SessionManager;

import java.util.Map;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "LoginViewModel";

    private MutableLiveData<LoginState> mLoginStateMutableData = new MutableLiveData<>();
    private MutableLiveData<String> mLoginErrorMutableData = new MutableLiveData<>();
    private String token;

    SessionManager sessionManager;
    IAccountsRepository mAccountsRepository;
    IUsersRepository mUsersRepository;

    public LoginViewModel(Application context) {
        super(context);
        mAccountsRepository = new AccountsRepository();
        mUsersRepository = new UsersRepository();
        sessionManager = new SessionManager(context);

        token = sessionManager.getUserData().get(sessionManager.KEY_TOKEN);
    }

    public void login(String email, String password)
    {
        mLoginStateMutableData.setValue(LoginState.START);
        Log.d("ViewModel", "Login State: " + mLoginStateMutableData.getValue());

        // Make a login request to the API to obtain a token
        // and store it in local storage
        mAccountsRepository.loginRemote(new LoginRequest(email, password), new ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                try {
                    token = loginResponse.getToken();
                    if(token == null) {
                        mLoginStateMutableData.setValue(LoginState.ERROR);
                        mLoginErrorMutableData.postValue("Server error. Please try again.");

                        return;
                    }

                    Log.d(TAG, "onResponse: " + token);

                    // Get the claims from the token
                    String payload = JwtParser.decoded(token);
                    Map<Object, Object> claims = decodeTokenClaims(payload);

                    Log.d(TAG, "onResponse: " + claims.get("id").toString() + " " + claims.get("email").toString());

                    // Create a session
                    sessionManager.createLoginSession(claims.get("id").toString(), claims.get("email").toString(), token);
                    // Fetch the user to retrieve additional data
                    fetchUser(claims.get("email").toString());
                } catch (Exception e) {
                    mLoginErrorMutableData.postValue("Server error: " + e.getMessage());
                    mLoginStateMutableData.setValue(LoginState.ERROR);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginStateMutableData.setValue(LoginState.ERROR);
                mLoginErrorMutableData.postValue("Server error: " + t.getMessage());
            }

            @Override
            public void onServerValidationFailure(ValidationErrorResponse errorValidationResponse) {
                mLoginStateMutableData.setValue(LoginState.ERROR);
                mLoginErrorMutableData.postValue("Server error: " + errorValidationResponse.getErrors().values());
            }

            @Override
            public void onServerFailure(ErrorResponse serverErrorResponse) {
                mLoginStateMutableData.setValue(LoginState.ERROR);
                mLoginErrorMutableData.postValue("Server error: " + serverErrorResponse.getMessage());
            }
        });
    }

    public void fetchUser(String email){

        mUsersRepository.getByEmail("Bearer "+ token, email, new IGetUser() {
            @Override
            public void onResponse(User userResponse) {
                sessionManager.addUserAvatar(userResponse.getAvatar());
                mLoginStateMutableData.setValue(LoginState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("LoginViewModel", "Response user: " + t.getMessage());
                mLoginStateMutableData.setValue(LoginState.ERROR);
                mLoginErrorMutableData.postValue("Server error: " + t.getMessage());
            }
        });
    }

    public LiveData<LoginState> getLoginState()
    {
        return mLoginStateMutableData;
    }

    public LiveData<String> getLoginError()
    {
        return mLoginErrorMutableData;
    }

    private Map<Object, Object> decodeTokenClaims(String payload) {
        try {
            String decodedPayload = JwtParser.decoded(payload);
        }
        catch (Exception e) {
            Log.d("LoginViewModel", "Claims decoding error: " + e.getMessage());
        }

        return new Gson().fromJson(payload, Map.class);
    }
}
