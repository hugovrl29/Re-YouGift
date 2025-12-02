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

import com.yougiftremake.yougift.entity.Dispatch;
import com.yougiftremake.yougift.entity.Peanut;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.repository.dispatch.DispatchRepository;
import com.yougiftremake.yougift.repository.peanut.PeanutRepository;
import com.yougiftremake.yougift.repository.wishlist.WishlistRepository;

@DataJpaTest
public class DispatchRepositoryTest {

    @Autowired
    private DispatchRepository dispatchRepository;

    @Autowired
    private PeanutRepository peanutRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    private Dispatch dispatch;

    private User user;
    private Wishlist wishlist;
    private Peanut peanut;

    @BeforeEach
    void setup(){

        user = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            "test",
            null,
            null,
            null
        );

        peanut = new Peanut(
            false,
            user,
            null,
            null,
            null
        );

        Peanut savedPeanut = peanutRepository.save(peanut);

        wishlist = new Wishlist(
            "test",
            "test",
            null,
            null,
            user
        );

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        dispatch = new Dispatch(
            user,
            savedWishlist,
            savedPeanut
        );

        dispatchRepository.save(dispatch);
    }

    @Test
    void shouldGetAllDispatchs(){
        // Act

        List<Dispatch> dispatchs = dispatchRepository.findAll();

        // Assert

        assertNotNull(dispatchs);
        assertEquals(1, dispatchs.size());
    }

    @Test
    void shouldGetByPeanutId(){
        // Act

        List<Dispatch> foundDispatch = dispatchRepository.findByPeanutId(peanut.getId());

        // Assert
        assertNotNull(foundDispatch);
        assertEquals(dispatch, foundDispatch.getFirst());
    }

    @Test
    void shouldSetPeanut(){
        // Arrange

        Peanut newPeanut = new Peanut(
            false,
            user,
            null,
            null,
            null
        );

        Peanut savedNewPeanut = peanutRepository.save(newPeanut);

        // Act

        dispatch.setPeanut(savedNewPeanut);

        // Assert

        assertEquals(savedNewPeanut, dispatch.getPeanut());
    }

    @Test
    void shouldSetUser(){
        // Arrange

        User newUser = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "test",
            "test",
            null,
            null,
            null
        );

        // Act

        dispatch.setUser(newUser);

        // Assert

        assertEquals(newUser, dispatch.getUser());
    }

    @Test
    void shouldSetWishlist(){
        // Arrange

        Wishlist newWishlist = new Wishlist(
            "test",
            "test",
            null,
            null,
            user
        );

        Wishlist savedNewWishlist = wishlistRepository.save(newWishlist);

        // Act

        dispatch.setWishlist(savedNewWishlist);

        // Assert

        assertEquals(savedNewWishlist, dispatch.getWishlist());
    }

    @Test
    void shouldDeleteDispatch(){
        // Arrange

        Long dispatchId = dispatch.getId();

        // Act
        dispatchRepository.deleteById(dispatchId);

        // Assert

        assertFalse(dispatchRepository.existsById(dispatchId));
    }

}
