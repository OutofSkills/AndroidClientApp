package com.intelligentcarmanagement.carmanagementclientapp.repositories.users;

import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IGetUser;
import com.intelligentcarmanagement.carmanagementclientapp.api.users.responses.IUpdateUser;
import com.intelligentcarmanagement.carmanagementclientapp.models.User;

public interface IUsersRepository {
    public void getByEmail(String email, IGetUser getUserResponse);
    public void update(int id, User user, IUpdateUser updateUserResponse);
}
