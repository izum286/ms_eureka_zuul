package com.izum286.search.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterServiceClientFallBack implements FilterServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterServiceClientFallBack.class);
    @Override
    public String getFilters() {
        LOGGER.error("Error during invocation of filter service ");
        return "Filter entity not provided... Something terrible happened";
    }
}
