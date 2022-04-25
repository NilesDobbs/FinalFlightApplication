package com.coderscampus.flightreservationapp.exception;

/**
 * Custom Exception
 */
public class UsernameExistException extends Exception {
    public UsernameExistException(String message) {
        super(message);
    }
}