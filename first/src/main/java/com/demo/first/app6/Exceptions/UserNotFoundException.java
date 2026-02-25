package com.demo.first.app6.Exceptions;

// Custom exception bna rhe hai
public class UserNotFoundException extends RuntimeException {

    // constructor
    public UserNotFoundException(String message) {
        super(message);
    }
}
