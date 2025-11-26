package com.yougiftremake.yougift.dto.peanut;

import jakarta.validation.constraints.NotBlank;

public record PeanutUpdateRequest(
    @NotBlank String name
) {

}
