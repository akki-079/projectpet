package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * OwnerApplication class
 * @author aksbhand2
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class OwnerApplication {
	/**
	 * Main Method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(OwnerApplication.class, args);
		
	}
	
	/**
	 * 
	 * @return new OpenAPI();
	 */
	@Bean
	public OpenAPI eCertOpenAPI() {
	    return new OpenAPI()
	            .info(new Info().title("Owner API")
	                    .description("Owner API for owner-service microservice")
	                    .version("v1.0.0"))
	            ;
	}
	

}
