package com.icebergarts.carwashagent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebergarts.carwashagent.model.RegistrationToken;
import com.icebergarts.carwashagent.repository.RegistrationTokenRepository;

@Service
public class RegistrationTokenServiceImplementation implements RegistrationTokenService {

	@Autowired
	RegistrationTokenRepository tokenRepository;

	@Override
	public RegistrationToken getByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public List<RegistrationToken> getAllTokens() {
		return tokenRepository.findAll();
	}

	@Override
	public RegistrationToken saveToken(RegistrationToken token) {
		return tokenRepository.save(token);
	}

}
