package com.fitflow.fitflow_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fitflow.fitflow_service")
public class FitflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitflowServiceApplication.class, args);
	}

}
