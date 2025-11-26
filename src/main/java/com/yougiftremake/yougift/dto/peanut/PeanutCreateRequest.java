package com.yougiftremake.yougift.dto.peanut;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PeanutCreateRequest(
    @NotBlank String name,
    @NotNull Long ownerId
) {

}
