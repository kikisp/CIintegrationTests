package com.icebergarts.carwashagent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {
	
	@GetMapping("/info")
    public String getMessage() {
        return "API WORKS";
    }
	
	@GetMapping("/secure")
    public String getSecureMessage() {
        return "THIS IS SECURE API";
    }

}
