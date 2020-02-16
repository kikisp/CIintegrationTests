package com.icebergarts.carwashagent.controller;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.icebergarts.carwashagent.config.CarwashProperties;
import com.icebergarts.carwashagent.model.AuthProvider;
import com.icebergarts.carwashagent.model.RegistrationToken;
import com.icebergarts.carwashagent.model.RoleProvider;
import com.icebergarts.carwashagent.model.User;
import com.icebergarts.carwashagent.payload.ApiResponse;
import com.icebergarts.carwashagent.payload.AuthResponse;
import com.icebergarts.carwashagent.payload.LoginRequest;
import com.icebergarts.carwashagent.payload.SignUpRequest;
import com.icebergarts.carwashagent.security.TokenProvider;
import com.icebergarts.carwashagent.service.EmailSenderService;
import com.icebergarts.carwashagent.service.RegistrationTokenService;
import com.icebergarts.carwashagent.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	 private static final Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RegistrationTokenService tokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	CarwashProperties washProperties;

	@Autowired
	private MessageSource messages;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, Locale locale) {
		logger.debug("REGISTERING USER");
		boolean userExists = userService.existsByEmail(signUpRequest.getEmail());
		if (userExists) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse(false, messages.getMessage("mail.inuse", null, locale)));
		}

		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.LOCAL);
		user.setRole(signUpRequest.getRole() != null ? RoleProvider.valueOf(signUpRequest.getRole()) : RoleProvider.USER);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User result = userService.saveUser(user);

		RegistrationToken registrationToken = new RegistrationToken(result.getId());
		tokenService.saveToken(registrationToken);
		sendMail(registrationToken.getToken(), result.getEmail(), locale);

		return ResponseEntity.ok(new ApiResponse(true, messages.getMessage("registration.success", null, locale)));
	}

	private void sendMail(String token, String recipient, Locale locale) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		try {
			mailMessage.setFrom(washProperties.getProperties().getMailSender());
			mailMessage.setTo(recipient);
			mailMessage.setSubject(messages.getMessage("mail.subject", null, locale));
			mailMessage.setText(messages.getMessage("mail.text", null, locale)
					+ washProperties.getAuth().getRegistrationVerificationApi() + token);
	
			emailSenderService.sendEmail(mailMessage);
		}catch (Exception e) {
			logger.error("Mail sending error",e);
		}
		
	}
}
