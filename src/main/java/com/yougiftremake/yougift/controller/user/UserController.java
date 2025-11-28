package com.yougiftremake.yougift.controller.user;

import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.yougiftremake.yougift.dto.user.*;
import com.yougiftremake.yougift.service.user.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CRUD Operations

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsersAsDTO();
        return ResponseEntity.ok(users);
    }
    

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest createRequest) {
        UserResponse userResponse = userService.createUserFromRequest(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest updateRequest) {
        UserResponse userResponse = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Additional Operations
    // Friends management

    @GetMapping("/{id}/get-friends")
    public ResponseEntity<List<UserResponse>> getFriends(@PathVariable Long id) {
        List<UserResponse> friends = userService.getFriendsOfUserAsDTO(id);
        return ResponseEntity.ok(friends);
    }
    


    @PostMapping("/{id}/add-friend/{friendId}")
    public ResponseEntity<UserResponse> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        UserResponse userResponse = userService.addFriend(id, friendId);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}/remove-friend/{friendId}")
    public ResponseEntity<UserResponse> removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        UserResponse userResponse = userService.removeFriend(id, friendId);
        return ResponseEntity.ok(userResponse);
    }

    // Wishlists management

    @GetMapping("/{id}/wishlists")
    public ResponseEntity<List<Long>> getUserWishlists(@PathVariable Long id) {
        List<Long> wishlistIds = userService.getWishlistsOfUser(id);
        return ResponseEntity.ok(wishlistIds);
    }

    // Peanuts management

    @GetMapping("/{id}/peanuts")
    public ResponseEntity<List<Long>> getUserPeanuts(@PathVariable Long id) {
        List<Long> peanutIds = userService.getPeanutsOfUser(id);
        return ResponseEntity.ok(peanutIds);
    }

    // Ban management

    @PostMapping("/{id}/ban")
    public ResponseEntity<UserResponse> changeBanStatus(@PathVariable Long id) {
        UserResponse userResponse = userService.changeBanStatusAndReturn(id);
        return ResponseEntity.ok(userResponse);
    }
    

}
