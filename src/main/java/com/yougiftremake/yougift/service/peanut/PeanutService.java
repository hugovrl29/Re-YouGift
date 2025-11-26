package com.yougiftremake.yougift.service.peanut;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.yougiftremake.yougift.dto.peanut.PeanutCreateRequest;
import com.yougiftremake.yougift.dto.peanut.PeanutResponse;
import com.yougiftremake.yougift.dto.peanut.PeanutUpdateRequest;
import com.yougiftremake.yougift.entity.Peanut;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.entity.Wishlist;
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
        Peanut peanut = new Peanut(null, false, owner, null, null);
        Peanut savedPeanut = peanutRepository.save(peanut);
        return toDTO(savedPeanut);
    }

    @Transactional
    public PeanutResponse updatePeanut(
        Long peanutId,
        PeanutUpdateRequest updateRequest
    ) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));

        Peanut updatedPeanut = peanutRepository.save(peanut);
        return toDTO(updatedPeanut);
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
                    peanut.getWishlists().remove(wishlist);
                    peanutRepository.save(peanut);
                },
                () -> {
                    throw new IllegalStateException(
                        "Wishlist with id " + wishlistId + " does not exist"
                    );
                }
            );
    }

    public Peanut distribute(Long peanutId) {
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

        // Random shift for distribution
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int shift = random.nextInt(allUsers.size() - 1) + 1;

        // New rotated list
        List<User> shuffledUsers = rotate(allUsers, shift);

        peanut.setUsers(shuffledUsers);
        peanut.setIsDistributed(true);

        return peanutRepository.save(peanut);
    }

    public List<User> rotate(List<User> list, int shift) {
        int size = list.size();
        List<User> rotated = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            rotated.add(list.get((i + shift) % size));
        }
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

    public List<User> getUsersInPeanut(Long peanutId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        return peanut.getUsers();
    }

    public List<Wishlist> getWishlistsInPeanut(Long peanutId) {
        Peanut peanut = peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
        return peanut.getWishlists();
    }

    public List<Peanut> getAllPeanuts() {
        return peanutRepository.findAll();
    }

    public List<Peanut> getPeanutsByOwnerId(Long ownerId) {
        return peanutRepository.findByOwnerId(ownerId);
    }

    public Peanut getPeanutById(Long peanutId) {
        return peanutRepository.findById(peanutId)
            .orElseThrow(() -> new IllegalStateException(
                "Peanut with id " + peanutId + " does not exist"
            ));
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
