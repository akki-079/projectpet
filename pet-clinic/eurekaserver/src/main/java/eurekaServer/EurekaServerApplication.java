package eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * EurekaServerApplicationClass
 * @author aksbhand2
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
	/**
	 * Main Method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);

	}

}
