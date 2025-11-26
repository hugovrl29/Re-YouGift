package com.yougiftremake.yougift.dto.peanut;

import java.util.List;

public record PeanutResponse(
    Long id,
    String name,
    Long ownerId,
    Boolean isDistributed,
    List<Long> memberIds,
    List<Long> wishlistIds
) {

}
