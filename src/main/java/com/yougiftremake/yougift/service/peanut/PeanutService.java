package com.yougiftremake.yougift.service.peanut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.peanut.*;
import com.yougiftremake.yougift.entity.Peanut;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
import com.yougiftremake.yougift.entity.Dispatch;
import com.yougiftremake.yougift.repository.peanut.PeanutRepository;
import com.yougiftremake.yougift.repository.user.UserRepository;
import com.yougiftremake.yougift.repository.wishlist.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class PeanutService {
    
    private final PeanutRepository peanutRepository;
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;

    public PeanutService(
        PeanutRepository peanutRepository,
        WishlistRepository wishlistRepository,
        UserRepository userRepository
    ) {
        this.peanutRepository = peanutRepository;
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PeanutResponse createPeanutFromRequest(PeanutCreateRequest createRequest) {
        User owner = userRepository.findById(createRequest.ownerId())
            .orElseThrow(() -> new IllegalStateException("User with id " + createRequest.ownerId() + " does not exist"));
        Peanut peanut = new Peanut(
            false,
            owner,
            new HashSet<>(),
            new HashSet<>(),
            null
        );
        Peanut savedPeanut = peanutRepository.save(peanut);
        return toDTO(savedPeanut);
    }

    public void deletePeanut(Long peanutId) {
        if (!peanutRepository.existsById(peanutId)) {
            throw new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            );
        }
        peanutRepository.deleteById(peanutId);
    }

    @Transactional
    public void addWishlistToPeanut(Long peanutId, Long wishlistId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        wishlistRepository.findById(wishlistId)
            .ifPresentOrElse(
                wishlist -> {
                    peanut.getWishlists().add(wishlist);
                    peanutRepository.save(peanut);
                },
                () -> {
                    throw new IllegalStateException(
                        "Wishlist with id " + wishlistId + " does not exist"
                    );
                }
            );
    }

    @Transactional
    public void removeWishlistFromPeanut(Long peanutId, Long wishlistId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        wishlistRepository.findById(wishlistId)
            .ifPresentOrElse(
                wishlist -> {
                    List<Wishlist> wishlistsList = new ArrayList<>(peanut.getWishlists());
                    wishlistsList.remove(wishlist);
                    Set<Wishlist> wishlists = new HashSet<>(wishlistsList);

                    peanut.setWishlists(wishlists);
                    peanutRepository.save(peanut);
                },
                () -> {
                    throw new IllegalStateException(
                        "Wishlist with id " + wishlistId + " does not exist"
                    );
                }
            );
    }

    public List<Wishlist> rotate(List<Wishlist> list, int shift) {
        int size = list.size();
        List<Wishlist> rotated = new ArrayList<>(size);
        Collections.rotate(rotated, shift);
        return rotated;
    }

    @Transactional
    public void addUserToPeanut(Long peanutId, Long userId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                "User with id " + userId + " does not exist"
            ));
        peanut.getUsers().add(user);
        peanutRepository.save(peanut);
    }

    @Transactional
    public void removeUserFromPeanut(Long peanutId, Long userId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        peanut.getUsers().removeIf(user -> user.getId().equals(userId));
        peanutRepository.save(peanut);
    }

    public List<Long> getUsersInPeanut(Long peanutId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        List<Long> userIds = new ArrayList<>();
        for (User user : peanut.getUsers()) {
            userIds.add(user.getId());
        }
        return userIds;
    }

    public List<Long> getWishlistsInPeanut(Long peanutId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        List<Long> wishlistIds = new ArrayList<>();
        for (Wishlist wishlist : peanut.getWishlists()) {
            wishlistIds.add(wishlist.getId());
        }
        return wishlistIds;
    }

    public List<Peanut> getAllPeanuts() {
        return peanutRepository.findAll();
    }

    public List<PeanutResponse> getAllPeanutsAsDTO() {
        return peanutRepository.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    public List<Peanut> getPeanutsByOwnerId(Long ownerId) {
        return peanutRepository.findByOwnerId(ownerId);
    }

    public List<PeanutResponse> getPeanutsByOwnerIdAsDTO(Long ownerId) {
        return peanutRepository.findByOwnerId(ownerId).stream()
            .map(this::toDTO)
            .toList();
    }

    public PeanutResponse getPeanutById(Long peanutId) {
        return toDTO(peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            )));
    }

    @Transactional
    public PeanutResponse distributeAndReturn(Long peanutId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        if (peanut.getIsDistributed()) {
            throw new IllegalStateException("Peanut is already distributed");
        }

        if (peanut.getWishlists().isEmpty()) {
            throw new IllegalStateException("Peanut has no wishlists to distribute");
        }
        List<User> allUsers = new ArrayList<>(peanut.getUsers());
        if (allUsers.size() < 2) {
            throw new IllegalStateException("Not enough users to distribute peanut");
        }
        List<Wishlist> allWishlists = new ArrayList<>(peanut.getWishlists());
        if (allWishlists.size() != allUsers.size()) {
            throw new IllegalStateException("There should be as much users as wishlists");
        }

        // Random shift for distribution
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int shift = random.nextInt(allUsers.size() - 1) + 1;

        // New wishlist order
        List<Wishlist> shuffledWishlists = rotate(allWishlists, shift);

        // New dispatch
        Set<Dispatch> newDispatchs = new HashSet<>();

        for (int i = 0; i < allUsers.size(); i++){
            User user = allUsers.get(i);
            Wishlist wishlist = shuffledWishlists.get(i);

            Dispatch dispatch = new Dispatch(
                user,
                wishlist,
                peanut
            );

            newDispatchs.add(dispatch);
        }

        Peanut savedPeanut = peanutRepository.save(peanut);
        return toDTO(savedPeanut);
    }

    public PeanutResponse toDTO(Peanut peanut) {
        List<Long> memberIds = new ArrayList<>();
        for (User user : peanut.getUsers()) {
            memberIds.add(user.getId());
        }
        List<Long> wishlistIds = new ArrayList<>();
        for (Wishlist wishlist : peanut.getWishlists()) {
            wishlistIds.add(wishlist.getId());
        }
        String ownerUsername = peanut.getOwner() != null ? peanut.getOwner().getUsername() : null;
        Long ownerId = peanut.getOwner() != null ? peanut.getOwner().getId() : null;
        return new PeanutResponse(
            peanut.getId(),
            ownerUsername,
            ownerId,
            peanut.getIsDistributed(),
            memberIds,
            wishlistIds
        );
    }

}
