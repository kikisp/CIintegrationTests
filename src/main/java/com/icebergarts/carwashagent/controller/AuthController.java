package com.icebergarts.carwashagent.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.icebergarts.carwashagent.config.CarwashProperties;
import com.icebergarts.carwashagent.exception.BadRequestException;
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
	public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity registerUser(@Valid @RequestBody SignUpRequest signUpRequest, Locale locale) {
		boolean userExists = userService.existsByEmail(signUpRequest.getEmail());
		if (userExists) {
			throw new BadRequestException(messages.getMessage("mail.inuse", null, locale));
		}

		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);
		if (signUpRequest.getRole() != null) {
			user.setRole(RoleProvider.valueOf(signUpRequest.getRole()));
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User result = userService.saveUser(user);

		RegistrationToken registrationToken = new RegistrationToken(result.getId());
		tokenService.saveToken(registrationToken);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(result.getEmail());
		mailMessage.setSubject(messages.getMessage("mail.subject", null, locale));
		mailMessage.setText(messages.getMessage("mail.text", null, locale)
				+ washProperties.getAuth().getRegistrationVerificationApi() + registrationToken.getToken());
		emailSenderService.sendEmail(mailMessage);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location)
				.body(new ApiResponse(true, messages.getMessage("registration.success", null, locale)));
	}

	@GetMapping("/confirm-account")
	public ResponseEntity confirmRegistration(@RequestParam("token") String token, Locale locale) {
		RegistrationToken registrationToken = tokenService.getByToken(token);

		if (registrationToken != null) {
			if (registrationToken.getExpiredTime().isBefore(LocalDateTime.now())) {
				return ResponseEntity.badRequest()
						.body(new ApiResponse(false, messages.getMessage("token.expired", null, locale)));
			}
			Optional<User> userOptional = userService.getById(registrationToken.getUserId());
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				user.setEnabled(true);
				userService.saveUser(user);
				registrationToken.setStatus(RegistrationToken.STATUS_VERIFIED);
				tokenService.saveToken(registrationToken);

			}
		}
		return ResponseEntity.badRequest()
				.body(new ApiResponse(false, messages.getMessage("user.notfound", null, locale)));

	}
}
