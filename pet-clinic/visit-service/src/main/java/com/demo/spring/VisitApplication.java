package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableEurekaClient
public class VisitApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitApplication.class, args);

	}
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public OpenAPI eCertOpenAPI() {
	    return new OpenAPI()
	            .info(new Info().title("Visit API")
	                    .description("Visit API for visit-service microservice")
	                    .version("v1.0.0"))
	            ;
	}

}
