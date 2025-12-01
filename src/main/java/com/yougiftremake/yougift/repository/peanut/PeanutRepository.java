package com.yougiftremake.yougift.repository.peanut;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yougiftremake.yougift.entity.Peanut;

@Repository
public interface PeanutRepository extends JpaRepository<Peanut, Long> {

    List<Peanut> findByOwnerId(Long ownerId);
}
