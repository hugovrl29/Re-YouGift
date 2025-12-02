package com.yougiftremake.yougift.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.yougiftremake.yougift.entity.Peanut;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.repository.user.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

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
        assertEquals("newpassword", savedUser.getPassword());
        assertEquals("newuser@test.com", savedUser.getEmail());
        assertEquals("New", savedUser.getFirstName());
        assertEquals("User", savedUser.getLastName());
        assertFalse(savedUser.getIsBanned());
        assertEquals(LocalDate.of(2000, 1, 1), savedUser.getDateOfBirth());
        assertEquals("Hello, I am new!", savedUser.getBio()); 
    }

    @Test
    void shouldCreateWishlist(){
        // Arrange

        Wishlist wishlist = new Wishlist(
            "Test",
            "Test",
            null,
            null,
            dupont
        );

        List<Wishlist> wishlistsList = new ArrayList<>();
        wishlistsList.add(wishlist);
        Set<Wishlist> wishlists = new HashSet<>(wishlistsList);

        // Act

        john.setWishlists(wishlists);

        // Assert
        assertTrue(john.getWishlists().contains(wishlist));
    }

    @Test
    void shouldCreatePeanut(){
        // Arrange

        Peanut peanut = new Peanut(
            false,
            dupont,
            null,
            null
        );

        List<Peanut> peanutsList = new ArrayList<>();
        peanutsList.add(peanut);
        Set<Peanut> peanuts = new HashSet<>(peanutsList);

        // Act

        john.setPeanuts(peanuts);

        // Assert
        assertTrue(john.getPeanuts().contains(peanut));
    }

    @Test
    void shouldAddFriend(){

        // Arrange
        List<User> johnFriendsList = new ArrayList<>();
        johnFriendsList.add(jane);
        Set<User> johnFriends = new HashSet<>(johnFriendsList);

        // Act

        john.setFriends(johnFriends);

        // Assert

        assertEquals(johnFriends, john.getFriends());
        assertTrue(john.getFriends().contains(jane));
    }

    @Test
    void shouldUpdateUser(){
        // Arrange

        User user = userRepository.findByUsername("john_doe");

        // Act

        user.setBio("New bio");

        // Assert

        assertNotNull(user.getBio());
        assertEquals("New bio", user.getBio());
    }

    @Test
    void shouldDeleteUser(){
        // Arrange

        Long id = 3L;

        // Act

        userRepository.deleteById(id);

        // Assert

        assertFalse(userRepository.existsById(id));
    }
}
