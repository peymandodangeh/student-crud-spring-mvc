package com.example.student.controller;

import com.example.student.security.Role;
import com.example.student.security.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    /** Start page after login depends on the role. */
    @GetMapping("/")
    public String home(@AuthenticationPrincipal SecurityUser user) {
        if (user.getRole() == Role.STUDENT) {
            return "redirect:/students/" + user.getStudentId();
        }
        return "redirect:/students";
    }

    /**
     * Spring Security forwards here when a logged-in user is not allowed.
     * Mapped for every HTTP method, because a denied POST is forwarded as a POST.
     */
    @RequestMapping("/access-denied")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied() {
        return "access-denied";
    }
}
