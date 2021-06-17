package com.spring.lgm0k7.factory.entity;

import com.spring.lgm0k7.core.entity.CoreEntity;

import javax.persistence.*;

@Entity
@Table(name = "factory", schema = "public")
public class FactoryEntity extends CoreEntity {
    @Column(name = "name")
    private String name;

    public FactoryEntity(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
