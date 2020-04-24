package com.izum286.search.repository;

import com.izum286.search.Models.FullCarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchRepository extends MongoRepository<FullCarEntity, String>, SearchRepositoryCustom {
    Page<FullCarEntity> findAllByPickUpPlaceWithin(Circle circle, Pageable pageable);

    List<FullCarEntity> findAllByOwnerEmail(String ownerEmail);


}
