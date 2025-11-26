package com.yougiftremake.yougift.dto.user;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    @Size(max = 50) String firstName,
    @Size(max = 50) String lastName,
    @Email String email,
    @Size(min = 6, max = 100) String password,
    LocalDate dateOfBirth,
    String profilePictureUrl
) {

}
