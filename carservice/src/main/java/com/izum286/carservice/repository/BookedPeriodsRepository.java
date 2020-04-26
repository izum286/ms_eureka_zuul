package com.izum286.carservice.repository;

import com.izum286.carservice.model.entity.BookedPeriodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookedPeriodsRepository extends MongoRepository<BookedPeriodEntity, String> {
}
