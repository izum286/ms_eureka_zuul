package com.izum286.carservice.Client;

import com.izum286.carservice.model.dto.AddUpdateCarDtoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "filterservice", fallback = FilterServiceClientFallBack.class)
public interface FilterServiceClient {

    @RequestMapping(value = "/get", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilters();


    @GetMapping()
    public void addFilter (AddUpdateCarDtoRequest addUpdateCarDtoRequest);
}
