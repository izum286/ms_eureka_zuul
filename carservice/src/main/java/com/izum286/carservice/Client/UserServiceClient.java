package com.izum286.carservice.Client;

import com.izum286.carservice.model.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "userservice", fallback = UserServiceClientFallback.class)
public interface UserServiceClient {

        @RequestMapping(value = "/get", method = RequestMethod.GET,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Optional<UserEntity> findById(String userEmail);
}
