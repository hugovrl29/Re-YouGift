package com.yougiftremake.yougift.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.repository.user.UserRepository;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User john;
    private User jane;
    private User dupont;

    @BeforeEach
    void setup() {
        john = new User(
            "john_doe",
            "password123",
            "johndoe@test.com",
            "John",
            "Doe",
            false,
            LocalDate.of(1990, 1, 1),
            "http://example.com/john.jpg",
            "Hello, I am John!",
            null,
            null,
            null
        );

        jane = new User(
            "jane_smith",
            "securepass",
            "janesmith@test.com",
            "Jane",
            "Smith",
            false,
            LocalDate.of(1985, 5, 15),
            "http://example.com/jane.jpg",
            "Hello, I am Jane!",
            null,
            null,
            null
        );

        dupont = new User(
            "dupont",
            "mypassword",
            "dupont@test.com",
            "Jean",
            "Dupont",
            true,
            LocalDate.of(1978, 9, 23),
            "http://example.com/dupont.jpg",
            "Bonjour, je suis dupont.",
            null,
            null,
            null
        );

        userRepository.save(john);
        userRepository.save(jane);
        userRepository.save(dupont);
    }

    @Test
    void shouldGetAllUsers(){

        // Act

        List<User> users = userRepository.findAll();

        // Assert

        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals("john_doe", users.get(0).getUsername());

    }

    @Test
    void shouldGetUserByUsername(){
        // Act

        User user = userRepository.findByUsername("john_doe");

        // Assert

        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("johndoe@test.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertFalse(user.getIsBanned());
        assertEquals(LocalDate.of(1990, 1, 1), user.getDateOfBirth());
        assertEquals("http://example.com/john.jpg", user.getProfilePictureUrl());
        assertEquals("Hello, I am John!", user.getBio());
    }

    @Test
    void shouldSaveUser() {
        // Arrange

        User user = new User(
            "new_user",
            "newpassword",
            "newuser@test.com",
            "New",
            "User",
            false,
            LocalDate.of(2000, 1, 1),
            "http://example.com/new.jpg",
            "Hello, I am new!",
            null,
            null,
            null
        );

        // Act

        User savedUser = userRepository.save(user);

        // Assert

        assertNotNull(savedUser.getId());
        assertEquals("new_user", savedUser.getUsername());
        
    }


}
