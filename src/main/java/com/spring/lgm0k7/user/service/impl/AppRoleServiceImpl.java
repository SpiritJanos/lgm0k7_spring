package com.spring.lgm0k7.user.service.impl;

import com.spring.lgm0k7.core.impl.CoreCRUDServiceImpl;
import com.spring.lgm0k7.user.entity.AppRoleEntity;
import com.spring.lgm0k7.user.service.AppRoleService;
import org.springframework.stereotype.Service;

@Service
public class AppRoleServiceImpl extends CoreCRUDServiceImpl<AppRoleEntity> implements AppRoleService {

    @Override
    protected void updateCore(AppRoleEntity updatableEntity, AppRoleEntity entity){
        updatableEntity.setAuthority(entity.getAuthority());
    }

    @Override
    protected Class<AppRoleEntity> getManagedClass(){return AppRoleEntity.class;}
}
