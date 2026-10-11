package com.example.student.controller;

import com.example.student.model.Course;
import com.example.student.service.CourseNotFoundException;
import com.example.student.service.CourseService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private static final String FORM_VIEW = "course-form";
    private static final String DUPLICATE_CODE = "این کد درس قبلاً ثبت شده است";

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setDisallowedFields("id");
    }

    /** Read: list all courses. */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", service.list());
        return "course-list";
    }

    /** Read: details of a single course. */
    @GetMapping("/{id}")
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("course", service.get(id));
        return "course-view";
    }

    /** Create: show an empty form. */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        return FORM_VIEW;
    }

    /** Create: handle form submission. */
    @PostMapping
    public String create(@Valid @ModelAttribute("course") Course course, BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return FORM_VIEW;
        }
        try {
            service.create(course);
        } catch (DuplicateKeyException e) {
            result.rejectValue("code", "duplicate", DUPLICATE_CODE);
            return FORM_VIEW;
        }
        redirect.addFlashAttribute("message", "درس با موفقیت اضافه شد");
        return "redirect:/courses";
    }

    /** Update: show the form filled with current data. */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        model.addAttribute("course", service.get(id));
        return FORM_VIEW;
    }

    /** Update: handle form submission. */
    @PostMapping("/{id}")
    public String update(@PathVariable long id, @Valid @ModelAttribute("course") Course course,
                         BindingResult result, RedirectAttributes redirect) {
        course.setId(id);
        if (result.hasErrors()) {
            return FORM_VIEW;
        }
        try {
            service.update(id, course);
        } catch (DuplicateKeyException e) {
            result.rejectValue("code", "duplicate", DUPLICATE_CODE);
            return FORM_VIEW;
        }
        redirect.addFlashAttribute("message", "درس با موفقیت ویرایش شد");
        return "redirect:/courses";
    }

    /** Delete. */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("message", "درس حذف شد");
        return "redirect:/courses";
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Model model) {
        model.addAttribute("notFoundTitle", "درس یافت نشد");
        model.addAttribute("backUrl", "/courses");
        return "not-found";
    }
}
