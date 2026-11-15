package com.example.student.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

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
