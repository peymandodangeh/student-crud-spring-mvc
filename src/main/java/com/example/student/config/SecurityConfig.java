package com.example.student.config;

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
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout"));
        // CSRF protection is enabled by default: every POST form must send the _csrf token
        return http.build();
    }

    /** Stores passwords as "{bcrypt}$2a$10$..." */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
