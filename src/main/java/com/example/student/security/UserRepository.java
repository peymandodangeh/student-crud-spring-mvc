package com.example.student.security;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private static final RowMapper<AppUser> ROW_MAPPER = (rs, rowNum) -> new AppUser(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password"),
            Role.valueOf(rs.getString("role")),
            rs.getObject("student_id", Long.class),
            rs.getBoolean("enabled"));

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<AppUser> findByUsername(String username) {
        return jdbc.query("SELECT id, username, password, role, student_id, enabled FROM app_user WHERE username = ?",
                ROW_MAPPER, username).stream().findFirst();
    }

    public boolean existsByUsername(String username) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM app_user WHERE username = ?", Integer.class, username);
        return count != null && count > 0;
    }

    public void insert(String username, String passwordHash, Role role, Long studentId) {
        jdbc.update("INSERT INTO app_user (username, password, role, student_id) VALUES (?, ?, ?, ?)",
                username, passwordHash, role.name(), studentId);
    }

    public void updateUsernameByStudentId(long studentId, String username) {
        jdbc.update("UPDATE app_user SET username = ? WHERE student_id = ?", username, studentId);
    }
}
