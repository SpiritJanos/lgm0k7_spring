package com.spring.lgm0k7.factory.controller;

import com.spring.lgm0k7.factory.controller.response.FactoryListResponse;
import com.spring.lgm0k7.factory.entity.FactoryEntity;
import com.spring.lgm0k7.factory.service.impl.FactoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FactoryController {

    @Autowired
    private FactoryServiceImpl service;

    @GetMapping(value = "/api/factory/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        FactoryEntity entity = service.findById(id);
        if(entity != null){
            return ResponseEntity.ok(entity);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/api/factory")
    public ResponseEntity<FactoryListResponse> findAll() {
        FactoryListResponse response = new FactoryListResponse();
        response.setFactories(service.findAll());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/api/factory", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FactoryEntity> create(@RequestBody FactoryEntity entity){
        service.create(entity);
        return ResponseEntity.ok(entity);
    }

    @PutMapping(value = "/api/factory", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FactoryEntity> update(@RequestBody FactoryEntity entity){
        return ResponseEntity.ok(service.update(entity));
    }

    @DeleteMapping(value = "/api/factory/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)){
            return ResponseEntity.ok("Sikeres törlés!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
