package com.ms.service_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    DiscoveryClient discoveryClient;


    public URI getEndpointForOtherService(String serviceId, String endpointName) throws ServiceUnavailableException {
        Optional<URI> serviceUri = discoveryClient.getInstances(serviceId)
                .stream()
                .map(service->service.getUri())
                .findFirst();
        URI endpoint = serviceUri.map(s -> s.resolve(endpointName)).orElseThrow(ServiceUnavailableException::new);
        return endpoint;
    }

    @GetMapping("/")
    public String hello(){
        return "hello from 1";
    }

    @GetMapping("/invoke2")
    public String invokeServ2() throws ServiceUnavailableException {
        RestTemplate template = new RestTemplate();
        return template.getForObject(getEndpointForOtherService("service2", "/hello"), String.class);
    }

}
