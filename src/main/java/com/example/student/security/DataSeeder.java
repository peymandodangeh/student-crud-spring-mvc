package com.example.student.security;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * Creates the demo accounts at startup (after the schema has been created).
 * DEMO ONLY: change these passwords in any real deployment.
 */
@Component
public class DataSeeder implements SmartInitializingSingleton {

    private final AccountService accounts;
    private final StudentRepository students;

    public DataSeeder(AccountService accounts, StudentRepository students) {
        this.accounts = accounts;
        this.students = students;
    }

    @Override
    public void afterSingletonsInstantiated() {
        accounts.createIfMissing("admin", "admin123", Role.ADMIN);
        accounts.createIfMissing("visitor", "visitor123", Role.VISITOR);

        // Students created before security was added get an account too
        for (Student student : students.findWithoutAccount()) {
            accounts.createStudentAccount(student);
        }
    }
}
