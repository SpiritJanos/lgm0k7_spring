package com.spring.lgm0k7.factory.controller.response;

import com.spring.lgm0k7.factory.entity.FactoryEntity;

import java.util.List;

public class FactoryListResponse {

    private List<FactoryEntity> factories;

    public FactoryListResponse(){
    }

    public FactoryListResponse(List<FactoryEntity> factories) {
        this.factories = factories;
    }

    public List<FactoryEntity> getFactories() {
        return factories;
    }

    public void setFactories(List<FactoryEntity> factories) {
        this.factories = factories;
    }
}
