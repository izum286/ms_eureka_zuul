package com.izum286.carservice.Client;

import com.izum286.carservice.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserServiceClientFallback implements UserServiceClient{

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterServiceClientFallBack.class);
    @Override
    public Optional<UserEntity> findById(String userEmail) {
        LOGGER.error("Error during invocation of User service ");
        return Optional.empty();
    }
}
