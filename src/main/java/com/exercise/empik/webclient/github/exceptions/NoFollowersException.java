package com.exercise.empik.webclient.github.exceptions;

public class NoFollowersException extends RuntimeException {
    public NoFollowersException(String login) {
        super("User with login: " + login + " has no followers. Calculations would throw Divide by zero exception.");
    }
}
