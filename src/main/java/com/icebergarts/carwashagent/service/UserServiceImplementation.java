package com.icebergarts.carwashagent.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebergarts.carwashagent.model.User;
import com.icebergarts.carwashagent.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> getById(UUID id) {
		return userRepository.findById(id);
	}

}
