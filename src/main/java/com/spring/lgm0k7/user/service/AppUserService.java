package com.spring.lgm0k7.user.service;

import com.spring.lgm0k7.core.CoreCRUDService;
import com.spring.lgm0k7.user.entity.AppUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends CoreCRUDService<AppUserEntity>, UserDetailsService {
}
