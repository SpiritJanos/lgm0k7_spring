package com.spring.lgm0k7.user.config;

import com.spring.lgm0k7.user.entity.AppRoleEntity;
import com.spring.lgm0k7.user.entity.AppUserEntity;
import com.spring.lgm0k7.user.service.AppRoleService;
import com.spring.lgm0k7.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Configuration
public class AppUserInitConfig {
    @Autowired
    private AppRoleService appRoleService;
    @Autowired
    private AppUserService appUserService;

    @PostConstruct
    private void init(){
        List<AppRoleEntity> roles = appRoleService.findAll();
        if (roles.isEmpty()){
            AppRoleEntity admin = new AppRoleEntity();
            admin.setAuthority("ROLE_ADMIN");
            appRoleService.create(admin);
            roles.add(admin);

            AppRoleEntity user = new AppRoleEntity();
            user.setAuthority("ROLE_USER");
            appRoleService.create(user);
            roles.add(user);
        }

        List<AppUserEntity> users = appUserService.findAll();
        if (users.isEmpty()){
            AppUserEntity adminEntity = new AppUserEntity();
            adminEntity.setFirstName("Nem");
            adminEntity.setLastName("Fontos");
            adminEntity.setUsername("admin");
            adminEntity.setPassword(new BCryptPasswordEncoder().encode("admin"));
            adminEntity.setAuthorities(new HashSet<>(roles));
            appUserService.create(adminEntity);

            AppUserEntity userEntity = new AppUserEntity();
            userEntity.setFirstName("Felhasználó");
            userEntity.setLastName("Név");
            userEntity.setUsername("user");
            userEntity.setPassword(new BCryptPasswordEncoder().encode("user"));
            AppRoleEntity userRole = roles.stream().filter(appRoleEntity -> appRoleEntity.getAuthority().equals("ROLE_USER")).findFirst().get();
            userEntity.setAuthorities(new HashSet<>(Collections.singletonList(userRole)));
            appUserService.create(userEntity);
        }
    }
}
