package com.example.student.security;

/**
 * A row of the app_user table. studentId is set only for STUDENT accounts.
 */
public record AppUser(Long id, String username, String password, Role role, Long studentId, boolean enabled) {
}
