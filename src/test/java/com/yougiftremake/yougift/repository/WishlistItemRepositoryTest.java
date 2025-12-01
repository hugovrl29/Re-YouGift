package com.yougiftremake.yougift.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

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


}
