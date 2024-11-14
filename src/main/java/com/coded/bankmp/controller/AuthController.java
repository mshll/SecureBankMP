package com.coded.bankmp.controller;

import com.coded.bankmp.bo.CreateUserRequest;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.bo.auth.AuthenticationResponse;
import com.coded.bankmp.bo.auth.CreateLoginRequest;
import com.coded.bankmp.bo.auth.LogoutResponse;
import com.coded.bankmp.service.UserService;
import com.coded.bankmp.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);

        // Check if the response is not null (indicating a successful creation)
        if (response != null) {
            // Return a 201 Created status code along with the created user data
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            // Handle the case where the creation was not successful (e.g., validation failed)
            // You can return a different status code or error message as needed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody CreateLoginRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authService.login(authenticationRequest);
        HttpStatus status = HttpStatus.OK;

        if (authenticationResponse == null) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(authenticationResponse, status);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutResponse auntenticationRequset) {
        authService.logout(auntenticationRequset);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
