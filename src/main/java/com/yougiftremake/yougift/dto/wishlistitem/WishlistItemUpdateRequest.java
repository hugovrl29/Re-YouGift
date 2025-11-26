package com.yougiftremake.yougift.dto.wishlistitem;

import jakarta.validation.constraints.Size;

public record WishlistItemUpdateRequest(
    @Size(max = 200) String name,
    @Size(max = 2000) String description
) {

}
