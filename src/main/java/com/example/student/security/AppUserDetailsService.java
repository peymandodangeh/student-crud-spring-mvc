package com.example.student.security;

import com.example.student.repository.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Called by Spring Security at login to load a user by username.
 * Spring Security itself then checks the password with the PasswordEncoder.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository users;
    private final StudentRepository students;

    public AppUserDetailsService(UserRepository users, StudentRepository students) {
        this.users = users;
        this.students = students;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String displayName = user.username();
        if (user.studentId() != null) {
            displayName = students.findById(user.studentId())
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .orElse(displayName);
        }
        return new SecurityUser(user, displayName);
    }
}
