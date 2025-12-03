package com.yougiftremake.yougift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yougiftremake.yougift.dto.peanut.PeanutCreateRequest;
import com.yougiftremake.yougift.dto.peanut.PeanutResponse;
import com.yougiftremake.yougift.entity.Peanut;
import com.yougiftremake.yougift.entity.User;
import com.yougiftremake.yougift.repository.peanut.PeanutRepository;
import com.yougiftremake.yougift.repository.user.UserRepository;
import com.yougiftremake.yougift.service.peanut.PeanutService;

@ExtendWith(MockitoExtension.class)
public class PeanutServiceTest {

    @Mock
    private PeanutRepository peanutRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PeanutService peanutService;

    @Test
    void shouldCreatePeanutFromRequest(){

        // Arrange

        Long userId = 1L;

        User user = new User(
            "test",
            "test",
            "test",
            "test",
            "test",
            false,
            LocalDate.now(),
            null,
            null,
            null,
            null,
            null
        );

        Peanut peanut = new Peanut(
            false,
            null,
            new HashSet<>(),
            new HashSet<>(),
            null
        );

        PeanutCreateRequest newPeanut = new PeanutCreateRequest(
            "test",
            userId
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(peanutRepository.save(any(Peanut.class))).thenReturn(peanut);

        // Act

        PeanutResponse createdPeanut = peanutService.createPeanutFromRequest(newPeanut);

        // Assert

        assertEquals(peanutService.toDTO(peanut), createdPeanut);
    }
}
