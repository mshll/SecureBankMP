package com.coded.bankmp.service.auth;

import com.coded.bankmp.bo.auth.AuthenticationResponse;
import com.coded.bankmp.bo.auth.CreateLoginRequest;
import com.coded.bankmp.bo.auth.LogoutResponse;
import com.coded.bankmp.entity.UserEntity;

public interface AuthService {

    AuthenticationResponse login(CreateLoginRequest createLoginRequest);

    void logout(LogoutResponse logoutResponse);

    UserEntity getAuthenticatedUser();
}
