package com.icebergarts.carwashagent.model;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {
	
	@Id @Type(type = "pg-uuid")
    private UUID id;
	
	 public BaseEntity() {
	        this.id = UUID.randomUUID();
	    }
	
}
