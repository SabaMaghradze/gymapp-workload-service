package com.gymapp.workload_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class GymappWorkloadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymappWorkloadServiceApplication.class, args);
	}

}
