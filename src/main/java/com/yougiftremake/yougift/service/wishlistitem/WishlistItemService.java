package com.yougiftremake.yougift.service.wishlistitem;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.wishlistitem.WishlistItemCreateRequest;
import com.yougiftremake.yougift.dto.wishlistitem.WishlistItemResponse;
import com.yougiftremake.yougift.dto.wishlistitem.WishlistItemUpdateRequest;
import com.yougiftremake.yougift.entity.WishlistItem;
import com.yougiftremake.yougift.repository.wishlistitem.WishlistItemRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistItemService {

    private final WishlistItemRepository wishlistItemRepository;

    public WishlistItemService(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional
    public WishlistItemResponse createWishlistItemFromRequest(WishlistItemCreateRequest createRequest) {
        if (wishlistItemRepository.existsByNameIgnoreCase(createRequest.name())) {
            throw new IllegalStateException("Wishlist Item with this name already exists");
        }
        WishlistItem wishlistItem = new WishlistItem(null, createRequest.name(), createRequest.description(), null);
        WishlistItem savedItem = wishlistItemRepository.save(wishlistItem);
        return toDTO(savedItem);
    }

    @Transactional
    public WishlistItemResponse updateWishlistItem(
        Long wishlistItemId,
        WishlistItemUpdateRequest updateRequest
    ) {
        WishlistItem wishlistItem = wishlistItemRepository.findById(wishlistItemId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist Item with id " + wishlistItemId + " does not exist"
            ));

        if(updateRequest.name() != null &&
           updateRequest.name().length() > 0 &&
           !updateRequest.name().equals(wishlistItem.getName())) {
            wishlistItem.setName(updateRequest.name());
        }
        if(updateRequest.description() != null &&
           !updateRequest.description().equals(wishlistItem.getDescription())) {
            wishlistItem.setDescription(updateRequest.description());
        }
        WishlistItem updatedItem = wishlistItemRepository.save(wishlistItem);
        return toDTO(updatedItem);
    }

    public void deleteWishlistItem(Long wishlistItemId) {
        if (!wishlistItemRepository.existsById(wishlistItemId)) {
            throw new IllegalStateException(
                "Wishlist Item with id " + wishlistItemId + " does not exist"
            );
        }
        wishlistItemRepository.deleteById(wishlistItemId);
    }

    public WishlistItem getWishlistItemById(Long wishlistItemId) {
        return wishlistItemRepository.findById(wishlistItemId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist Item with id " + wishlistItemId + " does not exist"
            ));
    }

    public WishlistItemResponse getWishlistItemByIdAsDTO(Long wishlistItemId) {
        return toDTO(wishlistItemRepository.findById(wishlistItemId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist Item with id " + wishlistItemId + " does not exist"
            )));
    }

    public List<WishlistItem> searchWishlistItemsByName(String name) {
        return wishlistItemRepository.findByNameContainingIgnoreCase(name);
    }

    public List<WishlistItemResponse> searchWishlistItemsByNameAsDTO(String name) {
        return wishlistItemRepository.findByNameContainingIgnoreCase(name).stream()
            .map(this::toDTO)
            .toList();
    }

    public List<WishlistItem> getAllWishlistItems() {
        return wishlistItemRepository.findAll();
    }

    public List<WishlistItemResponse> getAllWishlistItemsAsDTO() {
        return wishlistItemRepository.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    public WishlistItemResponse toDTO(WishlistItem wishlistItem) {
        List<Long> wishlistIds = wishlistItem.getWishlists().stream()
            .map(wishlist -> wishlist.getId())
            .toList();
        return new WishlistItemResponse(
            wishlistItem.getId(),
            wishlistItem.getName(),
            wishlistItem.getDescription(),
            wishlistIds
        );
    }
}
