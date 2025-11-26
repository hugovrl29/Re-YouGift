package com.yougiftremake.yougift.repository.wishlist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yougiftremake.yougift.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Wishlist> findByNameIgnoreCase(String name);

    List<Wishlist> findByNameContainingIgnoreCase(String name);

    List<Wishlist> findByOwnerId(Long ownerId);

}
