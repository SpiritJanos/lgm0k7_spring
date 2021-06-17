package com.spring.lgm0k7.car.service.impl;

import com.spring.lgm0k7.car.entity.CarEntity;
import com.spring.lgm0k7.car.service.CarService;
import com.spring.lgm0k7.core.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends CoreCRUDServiceImpl<CarEntity> implements CarService {

    @Override
    protected void updateCore(CarEntity updatableEntity, CarEntity entity){
        updatableEntity.setFactory(entity.getFactory());
        updatableEntity.setType(entity.getType());
        updatableEntity.setDoor(entity.getDoor());
        updatableEntity.setYear(entity.getYear());
    }

    @Override
    protected Class<CarEntity> getManagedClass(){ return CarEntity.class; }
}
