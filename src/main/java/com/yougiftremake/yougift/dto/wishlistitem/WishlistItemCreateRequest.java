package com.yougiftremake.yougift.dto.wishlistitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WishlistItemCreateRequest(
    @NotBlank @Size(max = 200) String name,
    @Size(max = 2000) String description
) {

}
