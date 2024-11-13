package com.example.demo.service;

import com.example.demo.bo.CreateUserRequest;
import com.example.demo.bo.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}
