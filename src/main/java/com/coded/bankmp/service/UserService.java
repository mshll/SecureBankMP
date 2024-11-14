package com.coded.bankmp.service;

import com.coded.bankmp.bo.*;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.util.TransactionType;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(UpdateUserProfileRequest request, UserEntity userEntity);

    List<UserEntity> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);

    TransactionResponse makeTransaction(Double amount, TransactionType type, UserEntity userEntity);

}
