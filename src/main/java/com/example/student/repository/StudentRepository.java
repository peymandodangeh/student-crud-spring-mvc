package com.example.student.repository;

import com.example.student.model.Student;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private static final String SELECT =
            "SELECT id, first_name, last_name, student_number, email, major FROM student";

    private static final RowMapper<Student> ROW_MAPPER = (rs, rowNum) -> new Student(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("student_number"),
            rs.getString("email"),
            rs.getString("major"));

    private final JdbcTemplate jdbc;

    public StudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Student> findAll() {
        return jdbc.query(SELECT + " ORDER BY id", ROW_MAPPER);
    }

    public List<Student> search(String keyword) {
        String like = "%" + keyword.toLowerCase() + "%";
        return jdbc.query(SELECT
                        + " WHERE LOWER(first_name) LIKE ? OR LOWER(last_name) LIKE ?"
                        + " OR student_number LIKE ? OR LOWER(email) LIKE ? OR LOWER(major) LIKE ?"
                        + " ORDER BY id",
                ROW_MAPPER, like, like, like, like, like);
    }

    /** Students enrolled in a course. */
    public List<Student> findByCourseId(long courseId) {
        return jdbc.query(SELECT + " JOIN enrollment e ON e.student_id = id WHERE e.course_id = ? ORDER BY last_name",
                ROW_MAPPER, courseId);
    }

    public Optional<Student> findById(long id) {
        return jdbc.query(SELECT + " WHERE id = ?", ROW_MAPPER, id).stream().findFirst();
    }

    public Student insert(Student s) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student (first_name, last_name, student_number, email, major) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setString(3, s.getStudentNumber());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getMajor());
            return ps;
        }, keyHolder);
        s.setId(keyHolder.getKey().longValue());
        return s;
    }

    public boolean update(Student s) {
        return jdbc.update(
                "UPDATE student SET first_name = ?, last_name = ?, student_number = ?, email = ?, major = ? WHERE id = ?",
                s.getFirstName(), s.getLastName(), s.getStudentNumber(), s.getEmail(), s.getMajor(), s.getId()) > 0;
    }

    public boolean deleteById(long id) {
        return jdbc.update("DELETE FROM student WHERE id = ?", id) > 0;
    }
}
