package com.study.security.filter;

import jakarta.servlet.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.logging.Logger;


public class LogAuthoritiesAfterFilter implements Filter {

    private final Logger logger = Logger.getLogger(LogAuthoritiesAfterFilter.class.getName());
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            logger.info("User " + auth.getName() + " is successfully authenticated and " + " has the authorities/roles " +
                    auth.getAuthorities());
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
