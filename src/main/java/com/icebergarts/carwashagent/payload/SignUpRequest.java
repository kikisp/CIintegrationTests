package com.icebergarts.carwashagent.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Data
public class SignUpRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    
    private String role;

    
}
