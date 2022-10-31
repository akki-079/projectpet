package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PetClinicFrontEndApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicFrontEndApplication.class, args);
	}
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/owner").setViewName("owner");
		registry.addViewController("/vet").setViewName("vet");
		registry.addViewController("/visit").setViewName("visit");
		registry.addViewController("/ownerPage").setViewName("ownerPage");
		registry.addViewController("/petPage").setViewName("petPage");
		registry.addViewController("/vetPage").setViewName("vetPage");
		registry.addViewController("/splPage").setViewName("splPage");
		registry.addViewController("/addVisitPage").setViewName("addVisitPage");
		registry.addViewController("/visitPage").setViewName("visitPage");
	}
}
