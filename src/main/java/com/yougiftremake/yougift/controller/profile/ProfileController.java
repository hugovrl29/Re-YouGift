package com.yougiftremake.yougift.controller.profile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yougiftremake.yougift.dto.user.UserResponse;
import com.yougiftremake.yougift.dto.user.UserUpdateRequest;
import com.yougiftremake.yougift.service.user.UserService;

import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


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
        return ResponseEntity.ok("User deleted successfully");
    }
}
