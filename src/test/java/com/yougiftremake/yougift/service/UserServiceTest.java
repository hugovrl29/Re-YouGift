package com.yougiftremake.yougift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yougiftremake.yougift.dto.user.UserCreateRequest;
import com.yougiftremake.yougift.dto.user.UserResponse;
import com.yougiftremake.yougift.dto.user.UserUpdateRequest;
import com.yougiftremake.yougift.entity.Peanut;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.repository.user.UserRepository;
import com.yougiftremake.yougift.service.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService UserService;

    private User savedUser;

    @BeforeEach
    void setup(){

        savedUser = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null,
            null,
            null
        );

    }

    @Test
    void shouldCreateUserFromRequest(){

        // Arrange

        UserCreateRequest newUser = new UserCreateRequest(
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            LocalDate.now()
        );

        when(userRepository.existsByUsername(newUser.username())).thenReturn(false);
        when(userRepository.existsByEmail(newUser.email())).thenReturn(false);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act

        UserResponse resultUser = UserService.createUserFromRequest(newUser);

        // Assert

        assertNotNull(resultUser);
        assertEquals(resultUser.username(), "test");
    }

    @Test
    void shouldUpdateUser(){
        // Arrange

        UserUpdateRequest newUser = new UserUpdateRequest(
            "newtest",
            "newtest",
            "newtest",
            "newtest",
            LocalDate.of(2000, 1, 1),
            "newtest"
        );

        when(userRepository.findById(savedUser.getId())).thenReturn(Optional.of(savedUser));

        // Act

        UserResponse resultUser = UserService.updateUser(savedUser.getId(), newUser);

        // Assert

        assertNotNull(resultUser);
        assertEquals(savedUser.getEmail(), newUser.email());
    }

    @Test
    void shouldDeleteUser(){
        // Arrange

        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        // Act

        UserService.deleteUser(userId);

        // Assert

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldGetUserDTOById(){

        // Arrange

        Long userId = 1L;

        User foundUser = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(foundUser));

        // Act

        UserResponse resultUser = UserService.getUserById(userId);

        // Assert

        assertNotNull(resultUser);
    }

    @Test
    void shouldGetUserByUsername(){
        User foundUser1 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        User foundUser2 = new User(
            "test",
            "test2",
            "test2",
            "test2",
            "test2",
            false,
            LocalDate.now(),
            "test2",
            null,
            null, 
            null,
            null
        );

        List<User> users = new ArrayList<>();
        users.add(foundUser1);
        users.add(foundUser2);

        when(userRepository.findByUsernameContainingIgnoreCase("test")).thenReturn(users);

        // Act

        List<User> resultUser = UserService.getUserByUsername("test");

        // Assert

        assertNotNull(resultUser);
    }

    @Test
    void shouldChangeBanStatus(){
        // Arrange

        Long userId = 1l;

        User foundUser = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(foundUser));

        // Act

        UserResponse resultUser = UserService.changeBanStatusAndReturn(userId);

        // Assert

        assertTrue(foundUser.getIsBanned());
        assertNotNull(resultUser);
    }

    @Test
    void shouldGetUsersFriends(){

        Long userId = 1L;
        User user1 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        User user2 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        User user3 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        user1.setFriends(Set.of(user2, user3));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));

        // Act

        List<UserResponse> friends = UserService.getFriendsOfUserAsDTO(userId);

        // Assert

        assertEquals(2, friends.size());
        assertTrue(friends.contains(UserService.toDTO(user2)));
        assertTrue(friends.contains(UserService.toDTO(user3)));
    }

    @Test
    void shouldGetAllFriendsAsDTO(){
        User user1 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        User user2 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            null, 
            null,
            null
        );

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act

        List<UserResponse> allUsers = UserService.getAllUsersAsDTO();

        // Assert

        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(UserService.toDTO(user1)));
        assertTrue(allUsers.contains(UserService.toDTO(user2)));
    }

    @Test
    void shouldAddFriend(){

        // Arrange

        Long user1Id = 1L;

        Long user2Id = 2L;

         User user1 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            new HashSet<>(), 
            null,
            null
        );

        User user2 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            new HashSet<>(), 
            null,
            null
        );

        when(userRepository.findById(user1Id)).thenReturn(Optional.of(user1));
        when(userRepository.findById(user2Id)).thenReturn(Optional.of(user2));

        // Act

        UserResponse friendAdded = UserService.addFriend(user1Id, user2Id);

        // Assert

        assertEquals(UserService.toDTO(user1), friendAdded);
        assertTrue(user1.getFriends().contains(user2));
        assertTrue(user2.getFriends().contains(user1));
    }

    @Test
    void shouldRemoveFriend(){

        // Arrange

        Long user1Id = 1L;
        Long user2Id = 2L;

        User user1 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            new HashSet<>(), 
            null,
            null
        );

        User user2 = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            new HashSet<>(), 
            null,
            null
        );

        user1.setFriends(Set.of(user2));
        user2.setFriends(Set.of(user1));

        when(userRepository.findById(user1Id)).thenReturn(Optional.of(user1));
        when(userRepository.findById(user2Id)).thenReturn(Optional.of(user2));

        // Act

        UserResponse friendRemoved = UserService.removeFriend(user1Id, user2Id);

        // Assert

        assertEquals(friendRemoved, UserService.toDTO(user1));
        assertFalse(user1.getFriends().contains(user2));
        assertFalse(user2.getFriends().contains(user1));
    }

    @Test
    void shouldGetWishlistsOfUser(){
        // Arrange

        Long userId = 1L;

        User user = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            new HashSet<>(), 
            null,
            null
        );

        Wishlist wish1 = new Wishlist(
            "test",
            "test",
            null,
            null,
            user
        );

        Wishlist wish2 = new Wishlist(
            "test",
            "test",
            null,
            null,
            user
        );

        user.setWishlists(Set.of(wish1, wish2));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        // Act

        List<Long> wishLists = UserService.getWishlistsOfUser(userId);

        // Assert

        assertEquals(2, wishLists.size());
        assertTrue(wishLists.contains(wish1.getId()));
        assertTrue(wishLists.contains(wish2.getId()));
    }

    @Test
    void shouldGetPeanutsOfUser(){

        //Arrange

        Long userId = 1L;

        User user = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            null,
            new HashSet<>(), 
            null,
            null
        );

        Peanut peanut1 = new Peanut(
            false,
            user,
            null,
            null,
            null
        );

        Peanut peanut2 = new Peanut(
            false,
            user,
            null,
            null,
            null
        );

        user.setPeanuts(Set.of(peanut1, peanut2));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<Long> peanutIds = UserService.getPeanutsOfUser(userId);

        // Assert

        assertEquals(2, peanutIds.size());
        assertTrue(peanutIds.contains(peanut1.getId()));
        assertTrue(peanutIds.contains(peanut2.getId()));

    }

}
