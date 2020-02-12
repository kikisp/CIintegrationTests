package com.icebergarts.carwashagent.service;

import java.util.Optional;
import java.util.UUID;

import com.icebergarts.carwashagent.model.User;

public interface UserService {

	User saveUser(User user);

	Optional<User> getById(UUID id);

	Optional<User> getByEmail(String email);

	Boolean existsByEmail(String email);

}
