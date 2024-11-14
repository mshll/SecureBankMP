package com.coded.bankmp.service.auth;

import com.coded.bankmp.bo.auth.AuthenticationResponse;
import com.coded.bankmp.bo.auth.CreateLoginRequest;
import com.coded.bankmp.bo.auth.LogoutResponse;
import com.coded.bankmp.bo.CustomUserDetails;
import com.coded.bankmp.config.JWTUtil;
import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.exception.BodyGuardException;
import com.coded.bankmp.exception.UserNotFoundException;
import com.coded.bankmp.repository.RoleRepository;
import com.coded.bankmp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JWTUtil jwtUtil;

    private final UserRepository userRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JWTUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    /*
    - It checks that the **`username`** and **`password`** in the **`authenticationRequest`** are not null or empty using the **`requiredNonNull`** method.
    - It converts the **`username`** to lowercase (standardizing it).
    - It calls the **`authenticate`** method to perform user authentication using Spring Security's **`AuthenticationManager`**.
    - It loads user details using the **`userDetailsService`**.
    - It generates an access token using the **`jwtUtil`**.
    - Finally, it constructs an **`AuthenticationResponse`** object with the user details and access token and returns it.
     */
    @Override
    public AuthenticationResponse login(CreateLoginRequest authenticationRequest) {
        requiredNonNull(authenticationRequest.getUsername(), "username");
        requiredNonNull(authenticationRequest.getPassword(), "password");

        String username = authenticationRequest.getUsername().toLowerCase();
        String password = authenticationRequest.getPassword();

        authenticate(username, password);

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String accessToken = jwtUtil.generateToken(userDetails);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setId(userDetails.getId());
        response.setUsername(userDetails.getUsername());
        //response.setPassword(userDetails.getPassword());
        response.setRole(userDetails.getRole());
        response.setToken("Bearer " + accessToken);

        return response;
    }


    @Override
    public void logout(LogoutResponse logoutResponse) {
        requiredNonNull(logoutResponse.getToken(), "token");
    }

    private void requiredNonNull(Object obj, String name) {
        if (obj == null || obj.toString().isEmpty()) {
            throw new BodyGuardException(name + " can't be empty");
        }
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect password");
        } catch (AuthenticationServiceException e) {
            throw new UserNotFoundException("Incorrect username");
        }
    }

    @Override
    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElse(null);
    }
}
