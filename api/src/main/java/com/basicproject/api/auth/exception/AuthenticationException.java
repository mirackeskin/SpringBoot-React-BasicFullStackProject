package com.basicproject.api.auth.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message){
        super(message);
    }
}
