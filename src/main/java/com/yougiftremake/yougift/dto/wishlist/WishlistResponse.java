package com.yougiftremake.yougift.dto.wishlist;

import java.util.List;

public record WishlistResponse(
    Long id,
    String name,
    String description,
    Long ownerId,
    Long peanutId,
    List<Long> itemIds
) {

}
