package com.izum286.filterservice.Repo;


import com.izum286.filterservice.Models.FilterNodeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends MongoRepository<FilterNodeEntity, String> {
}
