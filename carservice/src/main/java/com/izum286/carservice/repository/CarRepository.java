package com.izum286.carservice.repository;


import com.izum286.carservice.model.entity.FullCarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRepository extends MongoRepository<FullCarEntity, String> {
    /**
     * @author izum286
     * @param circle circle for search
     * @param pageable pageable param
     * @return Page<FullCarEntity>
     * status READY
     */
    Page<FullCarEntity> findAllByPickUpPlaceWithin(Circle circle, Pageable pageable);

    List<FullCarEntity> findAllByOwnerEmail(String ownerEmail);

}
