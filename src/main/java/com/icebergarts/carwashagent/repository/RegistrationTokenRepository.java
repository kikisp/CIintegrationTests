package com.icebergarts.carwashagent.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.icebergarts.carwashagent.model.RegistrationToken;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, UUID> {

	RegistrationToken findByToken(String token);

}
