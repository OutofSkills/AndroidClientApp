package com.intelligentcarmanagement.carmanagementclientapp.repositories.accounts;

import com.intelligentcarmanagement.carmanagementclientapp.api.login.ILoginResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.Login.LoginRequest;

public interface IAccountsRepository {
    public void loginRemote(LoginRequest loginRequest, ILoginResponse loginResponse);
}
