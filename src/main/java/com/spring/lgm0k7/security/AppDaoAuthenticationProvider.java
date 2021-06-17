package com.spring.lgm0k7.security;

import com.spring.lgm0k7.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private AppUserService userService;

    public AppDaoAuthenticationProvider () {
        setPasswordEncoder(new BCryptPasswordEncoder());

    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService){
        super.setUserDetailsService(userService);
    }
}
