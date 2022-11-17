package com.study.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestValidationBeforeFilter implements Filter {
    private Charset credentialsCharset = StandardCharsets.UTF_8;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpRes = (HttpServletResponse) servletResponse;
        String header = httpReq.getHeader("Authorization");
        if(header != null) {
            header = header.trim();
            if(StringUtils.startsWithIgnoreCase(header, "basic")) {
                byte[] base64token = header.substring(6).getBytes(credentialsCharset);
                byte[] decoded = null;
                try {
                    decoded = Base64.getDecoder().decode(base64token);
                    String token = new String(decoded,credentialsCharset );
                    int delim = token.indexOf(":");
                    if(delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    if(token.substring(0, delim).toLowerCase().contains("test")) {
                        httpRes.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }catch(IllegalArgumentException e) {
                    throw new BadCredentialsException("Failed to decode basic authentication toekn");
                }
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
