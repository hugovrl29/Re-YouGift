package com.yougiftremake.yougift.service.wishlist;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.wishlist.*;

import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.entity.WishlistItem;

import com.yougiftremake.yougift.repository.user.UserRepository;
import com.yougiftremake.yougift.repository.wishlist.WishlistRepository;
import com.yougiftremake.yougift.repository.wishlistitem.WishlistItemRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                            WishlistItemRepository wishlistItemRepository,
                            UserRepository userRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public WishlistResponse createWishlistFromRequest(WishlistCreateRequest createRequest) {
        if (wishlistRepository.existsByNameIgnoreCase(createRequest.name())) {
            throw new IllegalStateException("Wishlist with this name already exists");
        }
        User owner = userRepository.findById(createRequest.ownerId())
            .orElseThrow(() -> new IllegalStateException("User with id " + createRequest.ownerId() + " does not exist"));
        Wishlist wishlist = new Wishlist(createRequest.name(), createRequest.description(), null, null, owner);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return toDTO(savedWishlist);
    }

    @Transactional
    public WishlistResponse updateWishlist(
        Long wishlistId,
        WishlistUpdateRequest updateRequest
    ) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist with id " + wishlistId + " does not exist"
            ));

        if(updateRequest.name() != null &&
           updateRequest.name().length() > 0 &&
           !updateRequest.name().equals(wishlist.getName())) {
            wishlist.setName(updateRequest.name());
        }
        if(updateRequest.description() != null &&
           !updateRequest.description().equals(wishlist.getDescription())) {
            wishlist.setDescription(updateRequest.description());
        }
        Wishlist updatedWishlist = wishlistRepository.save(wishlist);
        return toDTO(updatedWishlist);
    }

    public void deleteWishlist(Long wishlistId) {
        if (!wishlistRepository.existsById(wishlistId)) {
            throw new IllegalStateException(
                "Wishlist with id " + wishlistId + " does not exist"
            );
        }
        wishlistRepository.deleteById(wishlistId);
    }

    public WishlistResponse getWishlistById(Long wishlistId) {
        return toDTO(wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist with id " + wishlistId + " does not exist"
            )));
    }

    public WishlistResponse getWishlistByIdAsDTO(Long wishlistId) {
        return toDTO(wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist with id " + wishlistId + " does not exist"
            )));
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public List<WishlistResponse> getAllWishlistsAsDTO() {
        return wishlistRepository.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    public List<Wishlist> getWishlistsByOwnerId(Long ownerId) {
        return wishlistRepository.findByOwnerId(ownerId);
    }

    public List<WishlistResponse> getWishlistsByOwnerIdAsDTO(Long ownerId) {
        return wishlistRepository.findByOwnerId(ownerId).stream()
            .map(this::toDTO)
            .toList();
    }

    @Transactional
    public WishlistResponse addItemToWishlist(Long wishlistId, Long itemId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist with id " + wishlistId + " does not exist"
            ));
        WishlistItem item = wishlistItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist Item with id " + itemId + " does not exist"
            ));
        wishlist.getItems().add(item);
        // keep both sides in sync
        if (item.getWishlists() != null) {
            item.getWishlists().add(wishlist);
        }
        Wishlist saved = wishlistRepository.save(wishlist);
        return toDTO(saved);
    }

    @Transactional
    public WishlistResponse removeItemFromWishlist(Long wishlistId, Long itemId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist with id " + wishlistId + " does not exist"
            ));
        WishlistItem item = wishlistItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist Item with id " + itemId + " does not exist"
            ));
        wishlist.getItems().remove(item);
        if (item.getWishlists() != null) {
            item.getWishlists().remove(wishlist);
        }
        Wishlist saved = wishlistRepository.save(wishlist);
        return toDTO(saved);
    }

    @Transactional
    public List<Long> getWishlistItemIds(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalStateException(
                "Wishlist with id " + wishlistId + " does not exist"
            ));
        return wishlist.getItems().stream()
            .map(WishlistItem::getId)
            .toList();
    }

    public WishlistResponse toDTO(Wishlist wishlist) {
        List<Long> itemIds = wishlist.getItems().stream()
            .map(WishlistItem::getId)
            .toList();
        return new WishlistResponse(
            wishlist.getId(),
            wishlist.getName(),
            wishlist.getDescription(),
            wishlist.getOwner().getId(),
            wishlist.getPeanut() != null ? wishlist.getPeanut().getId() : null,
            itemIds
        );
    }

}
