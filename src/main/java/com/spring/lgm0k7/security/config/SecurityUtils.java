package com.spring.lgm0k7.security.config;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public final class SecurityUtils {

    private SecurityUtils(){

    }

    static boolean isFrameworkInternalRequest(HttpServletRequest request){
        final String paramerterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return paramerterValue != null
                && Stream.of(ServletHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(paramerterValue));
    }

    public static boolean isUserLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public static boolean isAdmin(){
        return isUserLoggedIn() && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().
                anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
