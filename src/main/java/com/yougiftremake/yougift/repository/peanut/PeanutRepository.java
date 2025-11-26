package com.yougiftremake.yougift.repository.peanut;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yougiftremake.yougift.entity.Peanut;

@Repository
public interface PeanutRepository extends JpaRepository<Peanut, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Peanut> findByNameIgnoreCase(String name);

    List<Peanut> findByNameContainingIgnoreCase(String name);

    List<Peanut> findByOwnerId(Long ownerId);
}
