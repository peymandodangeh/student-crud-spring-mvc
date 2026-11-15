package com.example.student.security;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * The logged-in user (the "principal"). Besides username/password/roles it carries
 * the role and the linked student id, so pages and @PreAuthorize rules can use them,
 * e.g. authentication.principal.studentId
 */
public class SecurityUser extends User {

    private final Role role;
    private final Long studentId;
    private final String displayName;

    public SecurityUser(AppUser user, String displayName) {
        super(user.username(), user.password(), user.enabled(), true, true, true,
                List.of(new SimpleGrantedAuthority(user.role().authority())));
        this.role = user.role();
        this.studentId = user.studentId();
        this.displayName = displayName;
    }

    public Role getRole() {
        return role;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
