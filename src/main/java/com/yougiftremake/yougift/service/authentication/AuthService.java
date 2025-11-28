package com.yougiftremake.yougift.service.authentication;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.authentication.*;
import com.yougiftremake.yougift.dto.user.*;

import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.repository.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse register(UserCreateRequest createRequest) {
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
        return new UserResponse(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            savedUser.getProfilePictureUrl(),
            savedUser.getDateOfBirth()
        );
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.username());

        if (user == null) {
            throw new IllegalStateException("User not found");
        }

        if (user.getIsBanned()) {
            throw new IllegalStateException("User account is banned");
        }

        if (!user.getPassword().equals(loginRequest.password())) {
            throw new IllegalStateException("Invalid password");
        }

        return new LoginResponse("OK", user.getId());
    }

}
