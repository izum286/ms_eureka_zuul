package com.izum286.carservice.Client;

import com.izum286.carservice.model.dto.AddUpdateCarDtoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FilterServiceClientFallBack implements FilterServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterServiceClientFallBack.class);
    @Override
    public String getFilters() {
        LOGGER.error("Error during invocation of filter service ");
        return "Filter entity not provided... Something terrible happened with filterservice connection";
    }

    @Override
    public void addFilter(AddUpdateCarDtoRequest addUpdateCarDtoRequest) {
        LOGGER.error("Error during adding new filter");
    }
}
