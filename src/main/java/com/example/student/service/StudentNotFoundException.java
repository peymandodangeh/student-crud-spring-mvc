package com.example.student.service;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(long id) {
        super("Student not found: " + id);
    }
}
