package com.spring.lgm0k7.factory.service.impl;

import com.spring.lgm0k7.core.impl.CoreCRUDServiceImpl;
import com.spring.lgm0k7.factory.entity.FactoryEntity;
import org.springframework.stereotype.Service;
import com.spring.lgm0k7.factory.service.FactoryService;

@Service
public class FactoryServiceImpl extends CoreCRUDServiceImpl<FactoryEntity> implements FactoryService {

    @Override
    protected void updateCore(FactoryEntity updatableEntity, FactoryEntity entity){
        updatableEntity.setName(entity.getName());
    }

    @Override
    protected Class<FactoryEntity> getManagedClass() { return FactoryEntity.class; }
}
