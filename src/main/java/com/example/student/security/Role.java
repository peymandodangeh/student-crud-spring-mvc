package com.example.student.security;

public enum Role {

    /** Manages students, courses and enrollments. */
    ADMIN("مدیر"),
    /** Sees only their own profile and picks their own courses. */
    STUDENT("دانشجو"),
    /** Read-only access to students and courses. */
    VISITOR("بازدیدکننده");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /** Spring Security checks hasRole('ADMIN') against the authority "ROLE_ADMIN". */
    public String authority() {
        return "ROLE_" + name();
    }
}
