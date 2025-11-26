package com.yougiftremake.yougift.dto.user;

import java.time.LocalDate;

public record UserResponse(
    Long id,
    String username,
    String email,
    String firstName,
    String lastName,
    String profilePictureUrl,
    LocalDate dateOfBirth
) {

}
