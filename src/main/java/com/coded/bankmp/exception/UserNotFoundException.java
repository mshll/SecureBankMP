package com.coded.bankmp.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String str) {
        super(str);
    }
}
