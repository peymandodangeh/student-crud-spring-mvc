package com.example.student.config;

import jakarta.servlet.ServletContext;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Registers the "springSecurityFilterChain" filter in front of every request.
 * The security configuration itself is loaded by the root context (see WebAppInitializer).
 */
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        // Must run BEFORE Spring Security: the login filter reads request parameters,
        // and after the first read the request encoding can no longer be changed.
        insertFilters(servletContext, new CharacterEncodingFilter("UTF-8", true));
    }
}
