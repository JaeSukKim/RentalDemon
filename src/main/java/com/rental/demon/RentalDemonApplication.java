package com.rental.demon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
public class RentalDemonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalDemonApplication.class, args);
	}

}
