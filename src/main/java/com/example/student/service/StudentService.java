package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Student> list(String keyword) {
        return StringUtils.hasText(keyword) ? repository.search(keyword.trim()) : repository.findAll();
    }

    @Transactional(readOnly = true)
    public Student get(long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student create(Student student) {
        student.setId(null);
        return repository.insert(student);
    }

    public void update(long id, Student student) {
        student.setId(id);
        if (!repository.update(student)) {
            throw new StudentNotFoundException(id);
        }
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new StudentNotFoundException(id);
        }
    }
}
