package com.yougiftremake.yougift.controller.wishlist;

import org.springframework.web.bind.annotation.*;

import com.yougiftremake.yougift.dto.wishlist.*;
import com.yougiftremake.yougift.service.wishlist.WishlistService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    public WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // CRUD Operations

    @GetMapping("/{id}")
    public ResponseEntity<WishlistResponse> getWishlistById(@PathVariable Long id) {
        WishlistResponse wishlistResponse = wishlistService.getWishlistById(id);
        return ResponseEntity.ok(wishlistResponse);
    }

    @GetMapping("/all-wishlists")
    public ResponseEntity<List<WishlistResponse>> getAllWishlists() {
        List<WishlistResponse> wishlists = wishlistService.getAllWishlistsAsDTO();
        return ResponseEntity.ok(wishlists);
    }
    
    @PostMapping
    public ResponseEntity<WishlistResponse> createWishlist(@Valid @RequestBody WishlistCreateRequest createRequest) {
        WishlistResponse wishlistResponse = wishlistService.createWishlistFromRequest(createRequest);
        return ResponseEntity.ok(wishlistResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistResponse> updateWishlist(
        @PathVariable Long id,
        @Valid @RequestBody WishlistUpdateRequest updateRequest
    ) {
        WishlistResponse wishlistResponse = wishlistService.updateWishlist(id, updateRequest);
        return ResponseEntity.ok(wishlistResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }

    // Additional Operations
    // Item Management

    @GetMapping("/{id}/wishlist-items")
    public ResponseEntity<List<Long>> getWishlistItems(@PathVariable Long id) {
        List<Long> itemIds = wishlistService.getWishlistItemIds(id);
        return ResponseEntity.ok(itemIds);
    }

    @PostMapping("/{id}/add-item/{itemId}")
    public ResponseEntity<WishlistResponse> addItemToWishlist(
        @PathVariable Long id,
        @PathVariable Long itemId
    ) {
        WishlistResponse wishlistResponse = wishlistService.addItemToWishlist(id, itemId);
        return ResponseEntity.ok(wishlistResponse);
    }
    
    @PostMapping("/{id}/remove-item/{itemId}")
    public ResponseEntity<WishlistResponse> removeItemFromWishlist(
        @PathVariable Long id,
        @PathVariable Long itemId
    ) {
        WishlistResponse wishlistResponse = wishlistService.removeItemFromWishlist(id, itemId);
        return ResponseEntity.ok(wishlistResponse);
    }
    
    

}
