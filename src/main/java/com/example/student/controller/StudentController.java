package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.service.StudentNotFoundException;
import com.example.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String LIST_VIEW = "student-list";
    private static final String FORM_VIEW = "student-form";
    private static final String DUPLICATE_NUMBER = "این شماره دانشجویی قبلاً ثبت شده است";

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Trim input and convert empty strings to null
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        // The id always comes from the URL, never from the form
        binder.setDisallowedFields("id");
    }

    /** Read: list all students, optionally filtered by a search keyword. */
    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q, Model model) {
        model.addAttribute("students", service.list(q));
        model.addAttribute("q", q);
        return LIST_VIEW;
    }

    /** Read: details of a single student. */
    @GetMapping("/{id}")
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("student", service.get(id));
        return "student-view";
    }

    /** Create: show an empty form. */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return FORM_VIEW;
    }

    /** Create: handle form submission. */
    @PostMapping
    public String create(@Valid @ModelAttribute("student") Student student, BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return FORM_VIEW;
        }
        try {
            service.create(student);
        } catch (DuplicateKeyException e) {
            result.rejectValue("studentNumber", "duplicate", DUPLICATE_NUMBER);
            return FORM_VIEW;
        }
        redirect.addFlashAttribute("message", "دانشجو با موفقیت اضافه شد");
        return "redirect:/students";
    }

    /** Update: show the form filled with current data. */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        model.addAttribute("student", service.get(id));
        return FORM_VIEW;
    }

    /** Update: handle form submission. */
    @PostMapping("/{id}")
    public String update(@PathVariable long id, @Valid @ModelAttribute("student") Student student,
                         BindingResult result, RedirectAttributes redirect) {
        student.setId(id);
        if (result.hasErrors()) {
            return FORM_VIEW;
        }
        try {
            service.update(id, student);
        } catch (DuplicateKeyException e) {
            result.rejectValue("studentNumber", "duplicate", DUPLICATE_NUMBER);
            return FORM_VIEW;
        }
        redirect.addFlashAttribute("message", "اطلاعات دانشجو با موفقیت ویرایش شد");
        return "redirect:/students";
    }

    /** Delete. */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("message", "دانشجو حذف شد");
        return "redirect:/students";
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "not-found";
    }
}
