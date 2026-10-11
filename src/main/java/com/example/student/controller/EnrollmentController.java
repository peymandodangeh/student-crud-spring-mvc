package com.example.student.controller;

import com.example.student.service.CourseNotFoundException;
import com.example.student.service.EnrollmentException;
import com.example.student.service.EnrollmentService;
import com.example.student.service.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Enrolls a student in a course or removes the enrollment.
 * Both actions redirect back to the student's details page.
 */
@Controller
@RequestMapping("/students/{studentId}/courses")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    public String enroll(@PathVariable long studentId,
                         @RequestParam(name = "courseId", required = false) Long courseId,
                         RedirectAttributes redirect) {
        if (courseId == null) {
            redirect.addFlashAttribute("error", "یک درس انتخاب کنید");
        } else {
            try {
                service.enroll(studentId, courseId);
                redirect.addFlashAttribute("message", "درس برای دانشجو ثبت شد");
            } catch (EnrollmentException e) {
                redirect.addFlashAttribute("error", e.getMessage());
            }
        }
        return "redirect:/students/" + studentId;
    }

    @PostMapping("/{courseId}/delete")
    public String unenroll(@PathVariable long studentId, @PathVariable long courseId,
                           RedirectAttributes redirect) {
        service.unenroll(studentId, courseId);
        redirect.addFlashAttribute("message", "درس از برنامه‌ی دانشجو حذف شد");
        return "redirect:/students/" + studentId;
    }

    @ExceptionHandler({StudentNotFoundException.class, CourseNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(RuntimeException e, Model model) {
        if (e instanceof CourseNotFoundException) {
            model.addAttribute("notFoundTitle", "درس یافت نشد");
        }
        return "not-found";
    }
}
