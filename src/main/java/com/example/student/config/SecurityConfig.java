package com.example.student.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security: who may access which URL, and how users log in.
 * Users are loaded from the database by AppUserDetailsService.
 *
 * <pre>
 *                         ADMIN   VISITOR   STUDENT
 * list/view courses         ✓        ✓         ✓
 * list students             ✓        ✓         ✗
 * view a student            ✓        ✓       own only
 * create/edit/delete        ✓        ✗         ✗
 * enroll in courses       anyone     ✗       own only
 * </pre>
 * Rules are checked top to bottom; the first matching rule wins.
 * "own only" cannot be expressed with URLs alone: it is checked by @PreAuthorize in the controllers.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // The controller already passed the security check; let it forward to the JSP
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(antMatcher("/login"), antMatcher("/resources/**")).permitAll()

                        // Enrollment: must come BEFORE the general "POST /students/**" rule below
                        .requestMatchers(
                                antMatcher(POST, "/students/*/courses"),
                                antMatcher(POST, "/students/*/courses/*/delete")).hasAnyRole("ADMIN", "STUDENT")

                        // Create / edit / delete: ADMIN only
                        .requestMatchers(
                                antMatcher(GET, "/students/new"), antMatcher(GET, "/students/*/edit"),
                                antMatcher(GET, "/courses/new"), antMatcher(GET, "/courses/*/edit")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(POST, "/students/**"), antMatcher(POST, "/courses/**")).hasRole("ADMIN")

                        // Read-only pages
                        .requestMatchers(antMatcher(GET, "/students")).hasAnyRole("ADMIN", "VISITOR")
                        .requestMatchers(antMatcher(GET, "/students/*")).hasAnyRole("ADMIN", "VISITOR", "STUDENT")
                        .requestMatchers(antMatcher(GET, "/courses/**")).authenticated()

                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout"))
                // Logged in but not allowed -> 403 page
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied"));
        // CSRF protection is enabled by default: every POST form must send the _csrf token
        return http.build();
    }

    /** Stores passwords as "{bcrypt}$2a$10$..." */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
