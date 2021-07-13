package com.exercise.empik.webclient.github.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super("Not found user with login: " + login);
    }
}
