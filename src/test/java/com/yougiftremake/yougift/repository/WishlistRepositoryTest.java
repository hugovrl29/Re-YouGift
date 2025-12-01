package com.yougiftremake.yougift.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.repository.wishlist.WishlistRepository;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    private Wishlist wish;

    private User user;

    @BeforeEach
    void setup() {

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

        wish = new Wishlist(
            "Wishlist 1",
            " This is a wishlist",
            null,
            null,
            user

        );


        wishlistRepository.save(wish);
    }

    @Test
    void shouldGetAllWishlists(){
        // Act

        List<Wishlist> wishlists = wishlistRepository.findAll();

        // Assert

        assertNotNull(wishlists);
        assertEquals(1, wishlists.size());
        assertEquals("Wishlist 1", wishlists.get(0).getName());
    }

    @Test
    void shouldGetWishlistById(){
        // Act

        Long userId = user.getId();

        Wishlist wishlist = wishlistRepository.findByOwnerId(userId).getFirst();

        // Assert

        assertEquals("Wishlist 1", wishlist.getName());
    }

    @Test
    void shouldSaveWishlist(){
        // Arrange

        Wishlist wishlist = new Wishlist(
            "New wishlist",
            "This is a new wishlist",
            null,
            null,
            user
        );

        // Act

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        // Assert

        assertNotNull(savedWishlist.getId());
        assertEquals("New wishlist", savedWishlist.getName());
        assertEquals("This is a new wishlist", savedWishlist.getDescription());
        assertNull(savedWishlist.getItems());
        assertNull(savedWishlist.getPeanut());
        assertEquals(user, savedWishlist.getOwner());
    }

    @Test
    void shouldUpdateWishlist(){

        // Arrange

        Long userId = user.getId();

        Wishlist wishlist = wishlistRepository.findByOwnerId(userId).getFirst();

        // Act

        wishlist.setDescription("New description");

        // Assert

        assertEquals("New description", wishlist.getDescription());
    }

    @Test
    void shouldDeleteWishlist(){
        // Arrange

        Long userId = user.getId();

        // Act

        wishlistRepository.deleteById(userId);

        // Assert

        assertFalse(wishlistRepository.existsById(userId));
    }

}
