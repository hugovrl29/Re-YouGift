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
import com.yougiftremake.yougift.repository.peanut.PeanutRepository;

@DataJpaTest
public class PeanutRepositoryTest {

    @Autowired
    private PeanutRepository peanutRepository;

    private Peanut peanut1;
    private Peanut peanut2;

    private User user;

    @BeforeEach
    void setup(){
        user = new User(
            "Test",
            "Test",
            "Test",
            "Test",
            "Test",
            false,
            LocalDate.now(),
            "Test",
            "Test",
            null,
            null,
            null
        );

        peanut1 = new Peanut(
            false,
            user,
            null,
            null
        );

        peanut2 = new Peanut(
            false,
            user,
            null,
            null
        );

        peanutRepository.save(peanut1);
        peanutRepository.save(peanut2);
    }

    @Test
    void shouldGetAllPeanuts(){
        // Act
        List<Peanut> peanuts = peanutRepository.findAll();

        // Assert

        assertNotNull(peanuts);
        assertEquals(2, peanuts.size());
    }

    @Test
    void shouldGetPeanutById(){
        // Arrange

        Long userId = user.getId();
        // Act

        Peanut peanut = peanutRepository.findByOwnerId(userId).getFirst();

        // Assert

        assertEquals(user, peanut.getOwner());
    }

    @Test
    void shouldSavePeanut(){
        // Arrange
        Peanut peanut = new Peanut(false,
            user,
            null,
            null
        );

        // Act

        Peanut savedPeanut = peanutRepository.save(peanut);

        // Assert
        assertFalse(savedPeanut.getIsDistributed());
        assertEquals(user, savedPeanut.getOwner());
        assertNull(savedPeanut.getWishlists());
        assertNull(savedPeanut.getUsers());
    }

    @Test
    void shouldUpdatePeanut(){
        // Arrange

        Long userId = user.getId();

        Peanut peanut = peanutRepository.findByOwnerId(userId).getFirst();

        // Act

        peanut.setIsDistributed(true);

        // Assert

        assertTrue(peanut.getIsDistributed());
    }

    @Test
    void shouldSetUsers(){
        // Arrange
        User user = new User(
            "test",
            "Test",
            "test",
            "Test",
            "Test",
            false,
            LocalDate.now(),
            "Test",
            "Test",
            null,
            null,
            null
        );

        List<User> usersList = new ArrayList<>();
        usersList.add(user);
        Set<User> users = new HashSet<>(usersList);

        // Act

        peanut1.setUsers(users);

        // Assert

        assertEquals(users, peanut1.getUsers());
    }

    @Test
    void shouldDeletePeanut(){
        // Arrange

        Long userId = user.getId();

        // Act

        peanutRepository.deleteById(userId);

        // Assert

        assertFalse(peanutRepository.existsById(userId));
    }

}
