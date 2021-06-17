package com.spring.lgm0k7.core.impl;

import com.spring.lgm0k7.core.CoreCRUDService;
import com.spring.lgm0k7.core.entity.CoreEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class CoreCRUDServiceImpl<T extends CoreEntity> implements CoreCRUDService<T> {

    @Autowired
    protected EntityManager entityManager;

    public CoreCRUDServiceImpl(){
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT a FROM " + getManagedClass().getSimpleName() + " a", getManagedClass()).getResultList();
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        T factoryEntity = findById(id);
        if (factoryEntity == null){
            return false;
        }
        entityManager.remove(factoryEntity);
        return true;
    }

    @Override
    public T update(T entity){
        T updatebleCar = findById(entity.getId());
        if (updatebleCar != null){
            updateCore(updatebleCar, entity);
            entityManager.merge(updatebleCar);
        }
        return updatebleCar;
    }

    @Override
    public T findById(Long id){
        return entityManager.find(getManagedClass(), id);
    }


    protected abstract void updateCore(T updatableEntity, T entity);

    protected abstract Class<T> getManagedClass();
}
