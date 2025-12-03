package com.yougiftremake.yougift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yougiftremake.yougift.dto.peanut.PeanutCreateRequest;
import com.yougiftremake.yougift.dto.peanut.PeanutResponse;
import com.yougiftremake.yougift.entity.Peanut;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.repository.peanut.PeanutRepository;
import com.yougiftremake.yougift.repository.user.UserRepository;
import com.yougiftremake.yougift.repository.wishlist.WishlistRepository;
import com.yougiftremake.yougift.service.peanut.PeanutService;

@ExtendWith(MockitoExtension.class)
public class PeanutServiceTest {

    @Mock
    private PeanutRepository peanutRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private PeanutService peanutService;

    @Test
    void shouldCreatePeanutFromRequest(){

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
            null,
            null,
            null,
            null,
            null
        );

        Peanut peanut = new Peanut(
            false,
            null,
            new HashSet<>(),
            new HashSet<>(),
            null
        );

        PeanutCreateRequest newPeanut = new PeanutCreateRequest(
            userId
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(peanutRepository.save(any(Peanut.class))).thenReturn(peanut);

        // Act

        PeanutResponse createdPeanut = peanutService.createPeanutFromRequest(newPeanut);

        // Assert

        assertEquals(peanutService.toDTO(peanut), createdPeanut);
    }

    @Test
    void shouldDeletePeanut(){
        Long peanutId = 1L;

        when(peanutRepository.existsById(peanutId)).thenReturn(true);

        // Act

        peanutService.deletePeanut(peanutId);

        // Assert

        verify(peanutRepository, times(1)).existsById(peanutId);
        verify(peanutRepository, times(1)).deleteById(peanutId);

    }

    @Test
    void shouldAddWishlistToPeanut(){

        // Arrange

        Long peanutId = 1L;

        Long wishlistId = 1L;

        Peanut peanut = new Peanut(
            false,
            null,
            new HashSet<>(),
            new HashSet<>(),
            null
        );

        Wishlist wishlist = new Wishlist(
            "test",
            null,
            null,
            null,
            null
        );

        when(peanutRepository.findById(peanutId)).thenReturn(Optional.of(peanut));
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));
        when(peanutRepository.save(any(Peanut.class))).thenReturn(peanut);

        // Act

        peanutService.addWishlistToPeanut(peanutId, wishlistId);

        // Assert

        assertTrue(peanut.getWishlists().contains(wishlist));

    }

    @Test
    void shouldRemoveWishlistFromPeanut(){

        // Arrange

        Long peanutId = 1L;

        Long wishlistId = 1L;

        Peanut peanut = new Peanut(
            false,
            null,
            new HashSet<>(),
            new HashSet<>(),
            null
        );

        Wishlist wishlist = new Wishlist(
            "test",
            null,
            null,
            null,
            null
        );

        peanut.setWishlists(Set.of(wishlist));

        when(peanutRepository.findById(peanutId)).thenReturn(Optional.of(peanut));
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // Act

        peanutService.removeWishlistFromPeanut(peanutId, wishlistId);

        // Assert

        assertFalse(peanut.getWishlists().contains(wishlist));
    }
}
