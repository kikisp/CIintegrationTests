package com.icebergarts.carwashagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icebergarts.carwashagent.exception.ResourceNotFoundException;
import com.icebergarts.carwashagent.model.User;
import com.icebergarts.carwashagent.security.CurrentUser;
import com.icebergarts.carwashagent.security.UserPrincipal;
import com.icebergarts.carwashagent.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/user/me")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return userService.getByEmail(userPrincipal.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", userPrincipal.getEmail()));
	}
}
