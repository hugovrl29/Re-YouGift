package com.yougiftremake.yougift.dto.wishlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WishlistCreateRequest(
    @NotBlank @Size(max = 100) String name,
    @Size(max = 1000) String description,
    @NotNull Long ownerId
) {

}
