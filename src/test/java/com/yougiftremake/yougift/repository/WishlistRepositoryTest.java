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
import com.yougiftremake.yougift.entity.WishlistItem;
import com.yougiftremake.yougift.repository.wishlist.WishlistRepository;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    private Wishlist wish;
    private Wishlist wish2;

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

        wish2 = new Wishlist(
            "Wishlist 2",
            " This is another wishlist",
            null,
            null,
            user

        );


        wishlistRepository.save(wish);
        wishlistRepository.save(wish2);
    }

    @Test
    void shouldGetAllWishlists(){
        // Act

        List<Wishlist> wishlists = wishlistRepository.findAll();

        // Assert

        assertNotNull(wishlists);
        assertEquals(2, wishlists.size());
    }

    @Test
    void shouldGetWishlistById(){
        // Arrange

        Long userId = user.getId();

        // Act

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
    void shouldAddToPeanut(){

        // Arrange

        Peanut peanut = new Peanut(
            false,
            user,
            null,
            null
        );

        // Act

        wish.setPeanut(peanut);

        // Assert

        assertEquals(peanut, wish.getPeanut());
    }

    @Test
    void shouldSetOwner(){

        // Arrange

        Wishlist newWishlist = new Wishlist(
            "Test",
            "Test",
            null,
            null,
            user
        );

        User test = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            "Test",
            "Test",
            null,
            null,
            null
        );

        // Act

        newWishlist.setOwner(test);

        // Assert

        assertEquals(test, newWishlist.getOwner());
    }

    @Test
    void shouldAddItem(){

        // Arrange

        WishlistItem item = new WishlistItem(
            "Test",
            "test",
            null
        );

        List<WishlistItem> itemsList = new ArrayList<>();
        itemsList.add(item);
        Set<WishlistItem> items = new HashSet<>(itemsList);

        // Act

        wish.setItems(items);

        // Assert

        assertEquals(items, wish.getItems());
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
