package com.yougiftremake.yougift.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.user.UserCreateRequest;
import com.yougiftremake.yougift.dto.user.UserResponse;
import com.yougiftremake.yougift.dto.user.UserUpdateRequest;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.repository.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUserFromRequest(UserCreateRequest createRequest) {
        if (userRepository.existsByUsername(createRequest.username())) {
            throw new IllegalStateException("Username already taken");
        }
        if (userRepository.existsByEmail(createRequest.email())) {
            throw new IllegalStateException("Email already in use");
        }
        User user = new User(
            createRequest.username(),
            createRequest.password(),
            createRequest.email(),
            createRequest.firstName(),
            createRequest.lastName(),
            false,
            createRequest.dateOfBirth(),
            createRequest.profilePictureUrl(),
            null,
            null,
            null,
            null
        );
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    @Transactional
    public UserResponse updateUser(
        Long userId,
        UserUpdateRequest updateRequest
    ) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));

        if(updateRequest.email() != null &&
           updateRequest.email().length() > 0 &&
           !updateRequest.email().equals(user.getEmail())) {
            user.setEmail(updateRequest.email());
        }
        if(updateRequest.password() != null &&
           updateRequest.password().length() > 0 &&
           !updateRequest.password().equals(user.getPassword())) {
            user.setPassword(updateRequest.password());
        }
        if(updateRequest.firstName() != null &&
           updateRequest.firstName().length() > 0 &&
           !updateRequest.firstName().equals(user.getFirstName())) {
            user.setFirstName(updateRequest.firstName());
        }
        if(updateRequest.lastName() != null &&
           updateRequest.lastName().length() > 0 &&
           !updateRequest.lastName().equals(user.getLastName())) {
            user.setLastName(updateRequest.lastName());
        }
        if(updateRequest.dateOfBirth() != null &&
           !updateRequest.dateOfBirth().equals(user.getDateOfBirth())) {
            user.setDateOfBirth(updateRequest.dateOfBirth());
        }
        if(updateRequest.profilePictureUrl() != null &&
           updateRequest.profilePictureUrl().length() > 0 &&
           !updateRequest.profilePictureUrl().equals(user.getProfilePictureUrl())) {
            user.setProfilePictureUrl(updateRequest.profilePictureUrl());
        }
        User updatedUser = userRepository.save(user);
        return toDTO(updatedUser);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist"));
    }

    public List<User> getUserByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    @Transactional
    public void changeBanStatus(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        user.setIsBanned(!user.getIsBanned());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getFriendsOfUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        return user.getFriends();
    }

    @Transactional
    public void addFriend(User user, User friend) {
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
    }

    @Transactional
    public void removeFriend(User user, User friend) {
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userRepository.save(user);
        userRepository.save(friend);
    }

    public UserResponse toDTO(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getProfilePictureUrl(),
            user.getDateOfBirth()
        );
    }

}
