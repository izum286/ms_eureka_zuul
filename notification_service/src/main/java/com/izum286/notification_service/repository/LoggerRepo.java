package com.izum286.notification_service.repository;

import com.izum286.notification_service.models.LoggerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerRepo extends MongoRepository<LoggerInfo, String> {

}
