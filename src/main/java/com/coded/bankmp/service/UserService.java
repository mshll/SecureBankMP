package com.coded.bankmp.service;

import com.coded.bankmp.bo.CreateUserRequest;
import com.coded.bankmp.bo.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}
