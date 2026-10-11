package com.example.student.service;

/**
 * A business rule of enrollment was violated; the message is shown to the user.
 */
public class EnrollmentException extends RuntimeException {

    public EnrollmentException(String message) {
        super(message);
    }
}
