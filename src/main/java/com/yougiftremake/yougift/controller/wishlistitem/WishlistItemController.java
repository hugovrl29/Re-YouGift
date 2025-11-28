package com.yougiftremake.yougift.controller.wishlistitem;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yougiftremake.yougift.dto.wishlistitem.*;
import com.yougiftremake.yougift.service.wishlistitem.WishlistItemService;

@RestController
@RequestMapping("/api/wishlist-items")
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    public WishlistItemController(WishlistItemService wishlistItemService) {
        this.wishlistItemService = wishlistItemService;
    }

    // CRUD Operations

    @GetMapping("/{id}")
    public ResponseEntity<WishlistItemResponse> getWishlistItemById(@PathVariable Long id) {
        WishlistItemResponse wishlistItem = wishlistItemService.getWishlistItemById(id);
        return ResponseEntity.ok(wishlistItem);
    }

    @GetMapping("/all-items")
    public ResponseEntity<List<WishlistItemResponse>> getAllWishlistItems() {
        List<WishlistItemResponse> wishlistItems = wishlistItemService.getAllWishlistItemsAsDTO();
        return ResponseEntity.ok(wishlistItems);
    }
    

    @PostMapping("/{id}")
    public ResponseEntity<WishlistItemResponse> createWishlistItem(@RequestBody WishlistItemCreateRequest createRequest) {
        WishlistItemResponse wishlistItem = wishlistItemService.createWishlistItemFromRequest(createRequest);
        return ResponseEntity.ok(wishlistItem);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long id) {
        wishlistItemService.deleteWishlistItem(id);
        return ResponseEntity.noContent().build();
    }
    

}
