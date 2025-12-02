package com.yougiftremake.yougift.repository.wishlistitem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yougiftremake.yougift.entity.WishlistItem;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<WishlistItem> findById(Long id);

    Optional<WishlistItem> findByNameIgnoreCase(String name);

    List<WishlistItem> findByNameContainingIgnoreCase(String name);

    List<WishlistItem> findByWishlists_Id(Long wishlistId);

}
