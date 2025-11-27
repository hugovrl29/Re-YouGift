package com.yougiftremake.yougift.service.authentication;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.authentication.LoginRequest;
import com.yougiftremake.yougift.dto.authentication.LoginResponse;
import com.yougiftremake.yougift.dto.user.UserCreateRequest;
import com.yougiftremake.yougift.dto.user.UserResponse;
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
        UserResponse userResponse = new UserResponse(
            null,
            createRequest.username(),
            createRequest.email(),
            createRequest.firstName(),
            createRequest.lastName(),
            createRequest.profilePictureUrl(),
            createRequest.dateOfBirth()
        );
        return userResponse;
    }

    public LoginResponse login(LoginRequest loginRequest) {;
        User user = userRepository.findByUsername(loginRequest.username());

        if (!user.getPassword().equals(loginRequest.password())) {
            throw new IllegalStateException("Invalid password");
        }

        return new LoginResponse("OK", user.getId());
    }

}
