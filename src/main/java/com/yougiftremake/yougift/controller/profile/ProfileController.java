package com.yougiftremake.yougift.controller.profile;

import com.yougiftremake.yougift.dto.user.*;
import com.yougiftremake.yougift.service.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService profileService;

    public ProfileController(UserService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserResponse> getMyProfile(@RequestParam Long userId) {
        return ResponseEntity.ok(profileService.getUserById(userId));
    }

    @PatchMapping("/edit-my-profile")
    public ResponseEntity<UserResponse> editMyProfile(
        @RequestParam Long userId,
        @RequestBody UserUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(profileService.updateUser(userId, updateRequest));
    }

    @DeleteMapping("/edit-my-profile")
    public ResponseEntity<String> deleteMyProfile(@RequestParam Long userId) {
        profileService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
