package com.demo.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class PetClinicGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicGatewayApplication.class, args);

	}


}

