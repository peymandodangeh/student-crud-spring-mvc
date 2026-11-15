package com.example.student.security;

import com.example.student.model.Student;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Creates login accounts. Passwords are always stored hashed, never as plain text.
 */
@Service
@Transactional
public class AccountService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public AccountService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public void createIfMissing(String username, String rawPassword, Role role) {
        if (!users.existsByUsername(username)) {
            users.insert(username, passwordEncoder.encode(rawPassword), role, null);
        }
    }

    /** Student account: username and initial password are both the student number. */
    public void createStudentAccount(Student student) {
        users.insert(student.getStudentNumber(), passwordEncoder.encode(student.getStudentNumber()),
                Role.STUDENT, student.getId());
    }

    /** Keeps the username in sync when the student number changes. */
    public void updateStudentUsername(Student student) {
        users.updateUsernameByStudentId(student.getId(), student.getStudentNumber());
    }
}
