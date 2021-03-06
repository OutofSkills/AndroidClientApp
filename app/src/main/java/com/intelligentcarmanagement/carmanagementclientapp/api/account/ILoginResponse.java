package com.intelligentcarmanagement.carmanagementclientapp.api.account;

import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.api.errors.ValidationErrorResponse;
import com.intelligentcarmanagement.carmanagementclientapp.models.login.LoginResponse;

public interface ILoginResponse {
    void onResponse(LoginResponse loginResponse);
    void onFailure(Throwable t);

    void onServerValidationFailure(ValidationErrorResponse errorValidationResponse);

    void onServerFailure(ErrorResponse serverErrorResponse);
}
