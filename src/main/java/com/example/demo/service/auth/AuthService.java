package com.example.demo.service.auth;

import com.example.demo.bo.auth.AuthenticationResponse;
import com.example.demo.bo.auth.CreateLoginRequest;
import com.example.demo.bo.auth.LogoutResponse;

public interface AuthService {

    AuthenticationResponse login(CreateLoginRequest createLoginRequest);

    void logout(LogoutResponse logoutResponse);
}
