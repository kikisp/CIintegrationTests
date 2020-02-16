package com.icebergarts.carwashagent.controller;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.icebergarts.carwashagent.model.RegistrationToken;
import com.icebergarts.carwashagent.model.User;
import com.icebergarts.carwashagent.service.RegistrationTokenService;
import com.icebergarts.carwashagent.service.UserService;

@Controller
@RequestMapping("/verification")
public class VerificationController {
	
	private static final Logger logger = LogManager.getLogger(VerificationController.class);

	@Autowired
	UserService userService;

	@Autowired
	RegistrationTokenService tokenService;

	@Autowired
	private MessageSource messages;

	@GetMapping("/confirm-account")
	public String confirmRegistration(ModelMap model,@RequestParam("token") String token, Locale locale) {
		final String view = "verification";
		final String attribute = "message";
		RegistrationToken registrationToken = tokenService.getByToken(token);
		
		if (registrationToken == null) {
			model.addAttribute(attribute, messages.getMessage("token.invalid", null, locale));
		}else {
			if (registrationToken.getExpiredTime().isBefore(LocalDateTime.now())) {
				registrationToken.setStatus(RegistrationToken.STATUS_EXPIRED);
				tokenService.saveToken(registrationToken);
				model.addAttribute(attribute, messages.getMessage("token.expired", null, locale));
				return view;
			}
			Optional<User> userOptional = userService.getById(registrationToken.getUserId());
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				user.setEnabled(true);
				user.setEmailVerified(true);
				userService.saveUser(user);
				registrationToken.setStatus(RegistrationToken.STATUS_VERIFIED);
				tokenService.saveToken(registrationToken);
				model.addAttribute(attribute, messages.getMessage("user.verified", null, locale));

			}else {
				model.addAttribute(attribute, messages.getMessage("user.notfound", null, locale));
			}
		}
		
		return view;
}
}