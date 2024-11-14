package com.coded.bankmp.service;

import com.coded.bankmp.bo.CreateUserRequest;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword())); // todo fick fixa encoding!!!

        userEntity.setRole(request.getRole());
        userEntity = userRepository.save(userEntity);
        UserResponse response = new UserResponse(userEntity.getId(), userEntity.getName());
        return response;
    }
}
