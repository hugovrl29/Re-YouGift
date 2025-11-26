package com.yougiftremake.yougift.dto.wishlist;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WishlistUpdateRequest(
    Long id,
    @NotBlank @Size(max = 100) String name,
    @Size(max = 1000) String description,
    Long ownerId,
    Long peanutId,
    List<Long> itemIds
) {

}
