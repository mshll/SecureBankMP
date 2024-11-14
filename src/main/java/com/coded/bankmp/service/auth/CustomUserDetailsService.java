package com.coded.bankmp.service.auth;

import com.coded.bankmp.bo.CustomUserDetails;
import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.exception.UserNotFoundException;
import com.coded.bankmp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buildCustomUserDetailsOfUsername(username);
    }

    private CustomUserDetails buildCustomUserDetailsOfUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Incorrect Username"));

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(user.getId());
        userDetails.setUserName(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setRole(user.getRole());

        return userDetails;
    }
}
