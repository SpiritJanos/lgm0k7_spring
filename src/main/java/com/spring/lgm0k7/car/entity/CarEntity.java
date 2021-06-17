package com.spring.lgm0k7.car.entity;


import com.spring.lgm0k7.core.entity.CoreEntity;
import com.spring.lgm0k7.factory.entity.FactoryEntity;

import javax.persistence.*;

@Entity
@Table(name = "car", schema = "public")
public class CarEntity extends CoreEntity {

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "factory_id")
    private FactoryEntity factory;

    @Column(name = "doors")
    private Integer door;

    @Column(name = "year")
    private Integer year;

    public CarEntity(){
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public FactoryEntity getFactory() { return factory; }

    public void setFactory(FactoryEntity factory) { this.factory = factory; }

    public Integer getDoor() { return door; }

    public void setDoor(Integer door) { this.door = door; }

    public Integer getYear() { return year; }

    public void setYear(Integer year) { this.year = year; }
}
