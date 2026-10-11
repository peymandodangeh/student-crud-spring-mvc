package com.example.student.service;

import com.example.student.model.Course;
import com.example.student.repository.CourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Course> list() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Course get(long id) {
        return repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Course create(Course course) {
        course.setId(null);
        course.setCode(course.getCode().toUpperCase());
        return repository.insert(course);
    }

    public void update(long id, Course course) {
        course.setId(id);
        course.setCode(course.getCode().toUpperCase());
        if (!repository.update(course)) {
            throw new CourseNotFoundException(id);
        }
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new CourseNotFoundException(id);
        }
    }
}
