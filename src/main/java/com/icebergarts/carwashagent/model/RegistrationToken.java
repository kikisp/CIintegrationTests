package com.icebergarts.carwashagent.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "registration_tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = "token")
})
@Data
public class RegistrationToken extends BaseEntity {
	
	public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_VERIFIED = "VERIFIED";
    public static final String STATUS_EXPIRED = "EXPIRED";
    
    private UUID userId;
    private String token;
    private String status;
    private LocalDateTime issuedTime;
    private LocalDateTime expiredTime;
    
    
    public RegistrationToken(UUID userId){
    	this.userId = userId;
        this.token = UUID.randomUUID().toString();
        this.issuedTime = LocalDateTime.now();
        this.expiredTime = this.issuedTime.plusDays(1);
        this.status = STATUS_PENDING;
    }

}
