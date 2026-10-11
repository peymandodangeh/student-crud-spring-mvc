package com.example.student.service;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(long id) {
        super("Course not found: " + id);
    }
}
