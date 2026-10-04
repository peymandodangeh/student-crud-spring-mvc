package com.example.student.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Student {

    private Long id;

    @NotBlank(message = "نام الزامی است")
    @Size(max = 50, message = "نام حداکثر ۵۰ کاراکتر است")
    private String firstName;

    @NotBlank(message = "نام خانوادگی الزامی است")
    @Size(max = 50, message = "نام خانوادگی حداکثر ۵۰ کاراکتر است")
    private String lastName;

    @NotBlank(message = "شماره دانشجویی الزامی است")
    @Pattern(regexp = "\\d{6,12}", message = "شماره دانشجویی باید ۶ تا ۱۲ رقم باشد")
    private String studentNumber;

    @NotBlank(message = "ایمیل الزامی است")
    @Email(message = "ایمیل معتبر نیست")
    @Size(max = 100, message = "ایمیل حداکثر ۱۰۰ کاراکتر است")
    private String email;

    @Size(max = 100, message = "رشته تحصیلی حداکثر ۱۰۰ کاراکتر است")
    private String major;

    public Student() {
    }

    public Student(Long id, String firstName, String lastName, String studentNumber, String email, String major) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.email = email;
        this.major = major;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean isNew() {
        return id == null;
    }
}
