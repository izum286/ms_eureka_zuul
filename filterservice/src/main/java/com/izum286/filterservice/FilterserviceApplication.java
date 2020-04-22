package com.izum286.filterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableAsync
public class FilterserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilterserviceApplication.class, args);
	}

}
