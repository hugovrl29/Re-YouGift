package com.yougiftremake.yougift.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.entity.WishlistItem;
import com.yougiftremake.yougift.repository.wishlistitem.WishlistItemRepository;

@DataJpaTest
public class WishlistItemRepositoryTest {

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    private WishlistItem item1;
    private WishlistItem item2;

    @BeforeEach
    void setup(){
        item1 = new WishlistItem(
            "Item 1",
            "Item 1",
            null
        );

        item2 = new WishlistItem(
            "Item 2",
            "Item 2",
            null
        );

        wishlistItemRepository.save(item1);
        wishlistItemRepository.save(item2);
    }

    @Test
    void shouldGetAllItems(){
        // Act

        List<WishlistItem> items = wishlistItemRepository.findAll();

        // Assert

        assertNotNull(items);
        assertEquals(2, items.size());
    }

    @Test
    void shouldGetItemByWishlistId(){

        // Arrange

        User user = new User(
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

        List<WishlistItem> itemsList = new ArrayList<>();

        itemsList.add(item1);

        Set<WishlistItem> items = new HashSet<>(itemsList);

        Wishlist wishlist = new Wishlist(
            "Test",
            "Test",
            items,
            null,
            user
        );

        Long id = wishlist.getId();

        // Act

        WishlistItem item = wishlistItemRepository.findByWishlists_Id(id).getFirst();

        // Assert

        assertNotNull(item);
        assertEquals("Item 1", item.getName());

    }

    @Test
    void shouldFindById(){

        // Arrange

        Long itemId = item1.getId();

        // Act

        Optional<WishlistItem> item = wishlistItemRepository.findById(itemId);

        // Assert

        assertTrue(item.isPresent());
    }

    @Test
    void shouldSaveWishlistItem(){
        // Arrange

        WishlistItem item = new WishlistItem(
            "New",
            "New",
            null
        );

        // Act

        wishlistItemRepository.save(item);

        // Assert

        assertTrue(wishlistItemRepository.existsById(item.getId()));
    }

    @Test
    void shouldUpdateWishlistItem(){

        // Arrange

        User user = new User(
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

        List<WishlistItem> itemsList = new ArrayList<>();

        itemsList.add(item1);

        Set<WishlistItem> items = new HashSet<>(itemsList);

        Wishlist wishlist = new Wishlist(
            "Test",
            "Test",
            items,
            null,
            user
        );

        Long id = wishlist.getId();

        WishlistItem item = wishlistItemRepository.findByWishlists_Id(id).getFirst();

        // Act

        item.setDescription("New desc");

        // Assert

        assertEquals("New desc", item.getDescription());

    }

    @Test
    void shouldGetWishlists(){

        // Arrange

        Wishlist wishlist1 = new Wishlist(
            "test",
            "test",
            null,
            null, 
            null
        );

        Wishlist wishlist2 = new Wishlist(
            "test",
            "test",
            null,
            null, 
            null
        );

        List<Wishlist> wishlistList = new ArrayList<>();
        wishlistList.add(wishlist1);
        wishlistList.add(wishlist2);
        Set<Wishlist> wishlists = new HashSet<>(wishlistList);

        // Act

        item1.setWishlists(wishlists);

        // Assert

        assertEquals(2, item1.getWishlists().size());
        assertTrue(item1.getWishlists().contains(wishlist1));

    }

    @Test
    void shouldDeleteWishlistItem(){

        // Arrange

        Long itemId = item2.getId();

        // Act

        wishlistItemRepository.deleteById(itemId);

        // Assert

        assertFalse(wishlistItemRepository.existsByNameIgnoreCase(item2.getName()));
    }


}
