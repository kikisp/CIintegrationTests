package com.icebergarts.carwashagent.service;

import java.util.List;

import com.icebergarts.carwashagent.model.RegistrationToken;

public interface RegistrationTokenService {

	RegistrationToken saveToken(RegistrationToken token);

	RegistrationToken getByToken(String token);

	List<RegistrationToken> getAllTokens();

}
