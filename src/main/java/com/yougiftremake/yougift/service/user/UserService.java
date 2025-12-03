package com.yougiftremake.yougift.service.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.user.*;
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
            new HashSet<>(),
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

        return toDTO(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    public UserResponse getUserById(Long userId) {
        return toDTO(userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            )));
    }

    public List<User> getUserByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    @Transactional
    public UserResponse changeBanStatusAndReturn(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        user.setIsBanned(!user.getIsBanned());
        return toDTO(user);
    }

    public List<UserResponse> getFriendsOfUserAsDTO(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        return user.getFriends().stream()
            .map(this::toDTO)
            .toList();
    }

    public List<UserResponse> getAllUsersAsDTO() {
        return userRepository.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    @Transactional
    public UserResponse addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        User friend = userRepository.findById(friendId)
            .orElseThrow(() -> new IllegalStateException(
                "Friend with id " + friendId + " does not exist"
            ));
        
        if (userId.equals(friendId)) {
            throw new IllegalStateException("Cannot add yourself as a friend");
        }
        
        if (user.getFriends().contains(friend)) {
            throw new IllegalStateException("Already friends with this user");
        }

        List<User> userFriendsList = new ArrayList<>(user.getFriends());
        userFriendsList.add(friend);
        Set<User> userFriends = new HashSet<>(userFriendsList);

        List<User> friendFriendsList = new ArrayList<>(friend.getFriends());
        friendFriendsList.add(user);
        Set<User> friendFriends = new HashSet<>(friendFriendsList);
        
        user.setFriends(userFriends);
        friend.setFriends(friendFriends);
        userRepository.save(user);
        userRepository.save(friend);
        return toDTO(user);
    }

    @Transactional
    public UserResponse removeFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        User friend = userRepository.findById(friendId)
            .orElseThrow(() -> new IllegalStateException(
                "Friend with id " + friendId + " does not exist"
            ));
        
        if (!user.getFriends().contains(friend)) {
            throw new IllegalStateException("Not friends with this user");
        }

        List<User> userFriendsList = new ArrayList<>(user.getFriends());
        userFriendsList.remove(friend);
        Set<User> userFriends = new HashSet<>(userFriendsList);

        List<User> friendFriendsList = new ArrayList<>(friend.getFriends());
        friendFriendsList.remove(user);
        Set<User> friendFriends = new HashSet<>(friendFriendsList);

        user.setFriends(userFriends);
        friend.setFriends(friendFriends);

        userRepository.save(user);
        userRepository.save(friend);
        return toDTO(user);
    }

    public List<Long> getWishlistsOfUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        List<Long> wishlistIds = user.getWishlists().stream()
            .map(wishlist -> wishlist.getId())
            .toList();
        return wishlistIds;
    }

    public List<Long> getPeanutsOfUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        List<Long> peanutIds = user.getPeanuts().stream()
            .map(peanut -> peanut.getId())
            .toList();
        return peanutIds;
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
