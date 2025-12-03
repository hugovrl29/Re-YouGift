package com.yougiftremake.yougift.dto.peanut;

import jakarta.validation.constraints.NotNull;

public record PeanutCreateRequest(
    @NotNull Long ownerId
) {

}
