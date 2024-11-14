package com.coded.bankmp.service;

import com.coded.bankmp.bo.CreateUserRequest;
import com.coded.bankmp.bo.UpdateUserProfileRequest;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(UpdateUserProfileRequest request, UserEntity userEntity);

    List<UserEntity> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);
}
