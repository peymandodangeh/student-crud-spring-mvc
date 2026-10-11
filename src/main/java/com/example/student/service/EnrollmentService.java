package com.example.student.service;

import com.example.student.model.Course;
import com.example.student.model.Student;
import com.example.student.repository.CourseRepository;
import com.example.student.repository.StudentRepository;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manages the many-to-many relationship between students and courses.
 */
@Service
@Transactional
public class EnrollmentService {

    /** Maximum number of units a student can take. */
    public static final int MAX_UNITS = 20;

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<Course> coursesOf(long studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Course> availableCoursesFor(long studentId) {
        return courseRepository.findNotTakenByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Student> studentsOf(long courseId) {
        return studentRepository.findByCourseId(courseId);
    }

    public static int totalUnits(List<Course> courses) {
        return courses.stream().mapToInt(Course::getUnits).sum();
    }

    public void enroll(long studentId, long courseId) {
        studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        // Business rule: a student cannot take more than MAX_UNITS units
        int units = totalUnits(courseRepository.findByStudentId(studentId));
        if (units + course.getUnits() > MAX_UNITS) {
            throw new EnrollmentException("سقف " + MAX_UNITS + " واحد رعایت نشده است. واحدهای فعلی: " + units);
        }

        try {
            courseRepository.enroll(studentId, courseId);
        } catch (DuplicateKeyException e) {
            throw new EnrollmentException("دانشجو قبلاً این درس را اخذ کرده است");
        }
    }

    public void unenroll(long studentId, long courseId) {
        courseRepository.unenroll(studentId, courseId);
    }
}
