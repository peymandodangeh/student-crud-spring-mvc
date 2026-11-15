package com.example.student.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.SessionTrackingMode;
import java.util.EnumSet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Replaces web.xml: registers the DispatcherServlet.
 * The UTF-8 encoding filter is registered in SecurityWebAppInitializer, before Spring Security.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        // Keep the session id in a cookie only, never in URLs (;jsessionid=...)
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
