package com.yougiftremake.yougift.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record UserCreateRequest(
    @NotBlank String username,
    @NotBlank @Email String email,
    @NotBlank String password,
    String firstName,
    String lastName,
    String profilePictureUrl,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth
) {
    // Constructor and other methods can be added here if needed
    
}
