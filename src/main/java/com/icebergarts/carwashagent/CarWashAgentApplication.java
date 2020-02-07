package com.icebergarts.carwashagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.icebergarts.carwashagent.config.CarwashProperties;

@SpringBootApplication
@EnableConfigurationProperties(CarwashProperties.class)
public class CarWashAgentApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CarWashAgentApplication.class, args);
	}
	

}
