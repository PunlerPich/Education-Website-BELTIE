package com.example.demo.User.UserValidation;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

    // You can add additional constructors or methods as needed
}
