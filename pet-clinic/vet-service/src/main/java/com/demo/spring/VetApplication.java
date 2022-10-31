package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableEurekaClient
public class VetApplication {

	public static void main(String[] args) {
		SpringApplication.run(VetApplication.class, args);

	}
	
	@Bean
	public OpenAPI eCertOpenAPI() {
	    return new OpenAPI()
	            .info(new Info().title("Vet API")
	                    .description("Vet API for vet-service microservice")
	                    .version("v1.0.0"))
	            ;
	}

}
