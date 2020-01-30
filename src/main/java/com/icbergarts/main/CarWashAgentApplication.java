package com.icbergarts.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarWashAgentApplication {
	private static final Logger logger = LogManager.getLogger();
	public static void main(String[] args) {
		logger.info("APPLICATION STARTED!!");
		SpringApplication.run(CarWashAgentApplication.class, args);
	}

}
