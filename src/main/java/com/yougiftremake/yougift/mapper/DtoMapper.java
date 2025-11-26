package com.yougiftremake.yougift.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.yougiftremake.yougift.dto.user.UserResponse;
import com.yougiftremake.yougift.dto.wishlist.WishlistResponse;
import com.yougiftremake.yougift.dto.wishlistitem.WishlistItemResponse;
import com.yougiftremake.yougift.dto.peanut.PeanutResponse;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.entity.WishlistItem;
import com.yougiftremake.yougift.entity.Peanut;

public final class DtoMapper {

    private DtoMapper() {}

    public static UserResponse toUserResponse(User u) {
        if (u == null) return null;
        return new UserResponse(
            u.getId(),
            u.getUsername(),
            u.getEmail(),
            u.getFirstName(),
            u.getLastName(),
            u.getProfilePictureUrl(),
            u.getDateOfBirth()
        );
    }

    public static WishlistResponse toWishlistResponse(Wishlist w) {
        if (w == null) return null;
        List<Long> itemIds = null;
        if (w.getItems() != null) {
            itemIds = w.getItems().stream().map(WishlistItem::getId).collect(Collectors.toList());
        }
        Long ownerId = w.getOwner() != null ? w.getOwner().getId() : null;
        Long peanutId = w.getPeanut() != null ? w.getPeanut().getId() : null;
        return new WishlistResponse(
            w.getId(),
            w.getName(),
            w.getDescription(),
            ownerId,
            peanutId,
            itemIds
        );
    }

    public static WishlistItemResponse toWishlistItemResponse(WishlistItem wi) {
        if (wi == null) return null;
        List<Long> wishlistIds = null;
        if (wi.getWishlists() != null) {
            wishlistIds = wi.getWishlists().stream().map(Wishlist::getId).collect(Collectors.toList());
        }
        return new WishlistItemResponse(
            wi.getId(),
            wi.getName(),
            wi.getDescription(),
            wishlistIds
        );
    }

    public static PeanutResponse toPeanutResponse(Peanut p) {
        if (p == null) return null;
        List<Long> memberIds = null;
        if (p.getOwner() != null) {
            memberIds = List.of(p.getOwner().getId());
        }
        List<Long> wishlistIds = null;
        if (p.getWishlists() != null) {
            wishlistIds = p.getWishlists().stream().map(Wishlist::getId).collect(Collectors.toList());
        }
        return new PeanutResponse(
            p.getId(),
            null,
            p.getOwner() != null ? p.getOwner().getId() : null,
            p.getIsDistributed(),
            memberIds,
            wishlistIds
        );
    }
}
