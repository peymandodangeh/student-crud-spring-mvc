package com.example.student.repository;

import com.example.student.model.Course;
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
public class CourseRepository {

    private static final String SELECT = "SELECT c.id, c.code, c.title, c.units FROM course c";

    private static final RowMapper<Course> ROW_MAPPER = (rs, rowNum) -> new Course(
            rs.getLong("id"),
            rs.getString("code"),
            rs.getString("title"),
            rs.getInt("units"));

    private final JdbcTemplate jdbc;

    public CourseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Course> findAll() {
        return jdbc.query(SELECT + " ORDER BY c.code", ROW_MAPPER);
    }

    public Optional<Course> findById(long id) {
        return jdbc.query(SELECT + " WHERE c.id = ?", ROW_MAPPER, id).stream().findFirst();
    }

    public Course insert(Course c) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO course (code, title, units) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getCode());
            ps.setString(2, c.getTitle());
            ps.setInt(3, c.getUnits());
            return ps;
        }, keyHolder);
        c.setId(keyHolder.getKey().longValue());
        return c;
    }

    public boolean update(Course c) {
        return jdbc.update("UPDATE course SET code = ?, title = ?, units = ? WHERE id = ?",
                c.getCode(), c.getTitle(), c.getUnits(), c.getId()) > 0;
    }

    public boolean deleteById(long id) {
        // Related enrollment rows are removed by ON DELETE CASCADE
        return jdbc.update("DELETE FROM course WHERE id = ?", id) > 0;
    }

    // ---- Enrollment (many-to-many between student and course) ----

    /** Courses a student is enrolled in. */
    public List<Course> findByStudentId(long studentId) {
        return jdbc.query(SELECT + " JOIN enrollment e ON e.course_id = c.id WHERE e.student_id = ? ORDER BY c.code",
                ROW_MAPPER, studentId);
    }

    /** Courses a student is NOT enrolled in yet (options for the "add course" dropdown). */
    public List<Course> findNotTakenByStudentId(long studentId) {
        return jdbc.query(SELECT + " WHERE c.id NOT IN (SELECT course_id FROM enrollment WHERE student_id = ?)"
                + " ORDER BY c.code", ROW_MAPPER, studentId);
    }

    public void enroll(long studentId, long courseId) {
        jdbc.update("INSERT INTO enrollment (student_id, course_id) VALUES (?, ?)", studentId, courseId);
    }

    public boolean unenroll(long studentId, long courseId) {
        return jdbc.update("DELETE FROM enrollment WHERE student_id = ? AND course_id = ?", studentId, courseId) > 0;
    }
}
