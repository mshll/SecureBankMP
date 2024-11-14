package com.coded.bankmp.bo;

import com.coded.bankmp.entity.BankAccountEntity;
import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.util.Roles;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String role;

    public UserResponse(Long id, String username, String email, String phone, String address, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public UserResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.role = userEntity.getRole().getTitle().toString();
        this.email = userEntity.getEmail();
        this.phone = userEntity.getPhone();
        this.address = userEntity.getAddress();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
