package com.coderscampus.flightreservationapp.security.filter;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* This Filter is to logout if the application restart
 * without the application doesn't work correctly */

@Component
public class AutheticatedFilter extends GenericFilterBean {

    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /* if has no authentication object or is anonymous just follow the chain*/
        if (authentication == null ||
                (authentication != null  && authenticationTrustResolver.isAnonymous(authentication))){
            chain.doFilter (servletRequest, servletResponse);
            return;
        }
        /* If it has the authentication but the username is null redirect to /logout */
        if (authentication != null) {
            String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
            if (currentPrincipalName == null) {
                String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL(
                        servletRequest.getContextPath() + "/logout");
                servletResponse.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
                servletResponse.setHeader("Location", encodedRedirectURL);
            }
        }

        chain.doFilter (servletRequest, servletResponse);
        return;
    }
}
