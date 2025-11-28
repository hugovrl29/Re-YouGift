package com.yougiftremake.yougift.controller.peanut;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.yougiftremake.yougift.dto.peanut.PeanutCreateRequest;
import com.yougiftremake.yougift.dto.peanut.PeanutResponse;
import com.yougiftremake.yougift.dto.peanut.PeanutUpdateRequest;
import com.yougiftremake.yougift.service.peanut.PeanutService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;







@RestController
@RequestMapping("/api/peanuts")
public class PeanutController {

    private final PeanutService peanutService;

    public PeanutController(PeanutService peanutService) {
        this.peanutService = peanutService;
    }

    // CRUD Operations

    @GetMapping("/{id}")
    public ResponseEntity<PeanutResponse> getPeanutById(@PathVariable Long id) {
        PeanutResponse peanutResponse = peanutService.getPeanutById(id);
        return ResponseEntity.ok(peanutResponse);
    }

    @GetMapping("/all-peanuts")
    public ResponseEntity<List<PeanutResponse>> getAllPeanuts() {
        List<PeanutResponse> peanuts = peanutService.getAllPeanutsAsDTO();
        return ResponseEntity.ok(peanuts);
    }

    @PostMapping
    public ResponseEntity<PeanutResponse> createPeanut(@Valid @RequestBody PeanutCreateRequest createRequest) {
        PeanutResponse peanutResponse = peanutService.createPeanutFromRequest(createRequest);
        return ResponseEntity.ok(peanutResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeanutResponse> updatePeanut(
        @PathVariable Long id,
        @Valid @RequestBody PeanutUpdateRequest updateRequest
    ) {
        PeanutResponse peanutResponse = peanutService.updatePeanut(id, updateRequest);
        return ResponseEntity.ok(peanutResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeanut(@PathVariable Long id) {
        peanutService.deletePeanut(id);
        return ResponseEntity.noContent().build();
    }
    
    // Additional Operations
    // Wishlists Management

    @GetMapping("/{id}/peanut-wishlists")
    public ResponseEntity<List<Long>> getPeanutWishlists(@PathVariable Long id) {
        List<Long> wishlistIds = peanutService.getWishlistsInPeanut(id);
        return ResponseEntity.ok(wishlistIds);
    }

    @PostMapping("/{id}/add-wishlist/{wishlistId}")
    public ResponseEntity<PeanutResponse> addWishlistToPeanut(
        @PathVariable Long id,
        @RequestBody Long wishlistId
    ) {
        peanutService.addWishlistToPeanut(id, wishlistId);
        PeanutResponse peanutResponse = peanutService.getPeanutById(id);
        return ResponseEntity.ok(peanutResponse);
    }

    @PostMapping("/{id}/remove-wishlist/{wishlistId}")
    public ResponseEntity<PeanutResponse> removeWishlistFromPeanut(
        @PathVariable Long id,
        @RequestBody Long wishlistId
    ) {
        peanutService.removeWishlistFromPeanut(id, wishlistId);
        PeanutResponse peanutResponse = peanutService.getPeanutById(id);
        return ResponseEntity.ok(peanutResponse);
    }
    
    // Users Management

    @GetMapping("/{id}/peanut-users")
    public ResponseEntity<List<Long>> getPeanutUsers(@PathVariable Long id) {
        List<Long> userIds = peanutService.getUsersInPeanut(id);
        return ResponseEntity.ok(userIds);
    }
    

    @PostMapping("/{id}/add-user")
    public ResponseEntity<PeanutResponse> addUserToPeanut(
        @PathVariable Long id,
        @RequestBody Long userId
    ) {
        peanutService.addUserToPeanut(id, userId);
        PeanutResponse peanutResponse = peanutService.getPeanutById(id);
        return ResponseEntity.ok(peanutResponse);
    }
    
    @PostMapping("/{id}/remove-user")
    public ResponseEntity<PeanutResponse> removeUserFromPeanut(
        @PathVariable Long id,
        @RequestBody Long userId
    ) {
        peanutService.removeUserFromPeanut(id, userId);
        PeanutResponse peanutResponse = peanutService.getPeanutById(id);
        return ResponseEntity.ok(peanutResponse);
    }

    // Distribution

     @PostMapping("/{id}/distribute")
    public ResponseEntity<PeanutResponse> distributePeanut(@PathVariable Long id) {
        PeanutResponse peanutResponse = peanutService.distributeAndReturn(id);
        return ResponseEntity.ok(peanutResponse);
    }

}
