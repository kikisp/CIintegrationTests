package com.icbergarts.CarWashAgent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CarWashAgentApplicationTests {
	
	private static final String testString ="Context loaded!";

	@Test
	void contextLoads() {
		assertEquals("Context loaded!", testString);
	}

}
