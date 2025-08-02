package com.example.household_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages= "com.example.household_server.infrastructure")
public class HouseholdServerApplication {
	


	public static void main(String[] args) {
		try{
			SpringApplication.run(HouseholdServerApplication.class, args);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

}
