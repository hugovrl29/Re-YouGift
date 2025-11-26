package com.yougiftremake.yougift.dto.wishlistitem;

import java.util.List;

public record WishlistItemResponse(
    Long id,
    String name,
    String description,
    List<Long> wishlistIds
) {

}
