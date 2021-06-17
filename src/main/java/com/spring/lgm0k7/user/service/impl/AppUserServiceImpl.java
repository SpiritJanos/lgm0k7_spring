package com.spring.lgm0k7.user.service.impl;

import com.spring.lgm0k7.core.impl.CoreCRUDServiceImpl;
import com.spring.lgm0k7.user.entity.AppUserEntity;
import com.spring.lgm0k7.user.service.AppUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;

@Service
public class AppUserServiceImpl extends CoreCRUDServiceImpl<AppUserEntity> implements AppUserService {
    @Override
    protected void updateCore(AppUserEntity updatableEntity, AppUserEntity entity){
        updatableEntity.setFirstName(entity.getFirstName());
        updatableEntity.setLastName(entity.getLastName());
        updatableEntity.setUsername(entity.getUsername());
        updatableEntity.setPassword(entity.getPassword());
        updatableEntity.setAuthorities(entity.getAuthorities());
    }

    @Override
    protected Class<AppUserEntity> getManagedClass(){return AppUserEntity.class;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            TypedQuery<AppUserEntity> query = entityManager.createNamedQuery(AppUserEntity.FIND_USER_BY_USER_NAME, AppUserEntity.class);
            query.setParameter("username", username);

            return query.getSingleResult();
        } catch (Exception e){
            throw new UsernameNotFoundException("The given user cannot be find: " + username);
        }
    }
}
