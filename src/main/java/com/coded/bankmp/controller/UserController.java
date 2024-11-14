package com.coded.bankmp.controller;

import com.coded.bankmp.bo.TransactionResponse;
import com.coded.bankmp.bo.UpdateUserProfileRequest;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.repository.UserRepository;
import com.coded.bankmp.service.UserService;
import com.coded.bankmp.service.auth.AuthServiceImpl;
import com.coded.bankmp.util.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    @Autowired
    private AuthServiceImpl authServiceImpl;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/sayHi")
    public String sayHi() {
        return "Hi, you are an authenticated user";
    }

    @PutMapping("/update-profile")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody UpdateUserProfileRequest request) {
        UserEntity userEntity = authServiceImpl.getAuthenticatedUser();

        UserResponse response = userService.updateUser(request, userEntity);

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

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {
        UserEntity userEntity = authServiceImpl.getAuthenticatedUser();

        if (userEntity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(userEntity));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/transaction/{type}/{amount}")
    public ResponseEntity<TransactionResponse> makeTransaction(@PathVariable("type") String type, @PathVariable("amount") Double amount) {
        UserEntity userEntity = authServiceImpl.getAuthenticatedUser();
        TransactionResponse transactionResponse = userService.makeTransaction(amount, TransactionType.valueOf(type.toUpperCase()), userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }
}
