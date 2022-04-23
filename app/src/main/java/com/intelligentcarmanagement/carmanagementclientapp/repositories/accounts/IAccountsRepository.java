package com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts;

import com.intelligentcarmanagement.carmanagementclientapp.api.account.ILoginResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.account.IRegisterRequest;
import com.intelligentcarmanagement.carmanagementclientapp.api.account.IRegisterResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.Login.LoginRequest;
import com.intelligentcarmanagement.carmanagementclientapp.models.RegisterRequest;

public interface IAccountsRepository {
    public void loginRemote(LoginRequest loginRequest, ILoginResponse loginResponse);

    public void register(RegisterRequest registerRequest, IRegisterResponse registerResponse);
}
