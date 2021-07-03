package com.example.kakaopay_membership;

import com.example.kakaopay_membership.exception.UserNotFoundException;
import com.example.kakaopay_membership.user.entity.User;
import com.example.kakaopay_membership.user.repository.UserRepository;
import com.example.kakaopay_membership.user.service.UserService;
import com.example.kakaopay_membership.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {

    UserService userService;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @DisplayName("사용자 조회")
    @Test
    void testFindUser() {
        User user = new User("test1");
        BDDMockito.given(userRepository.findByUserId(any()))
                .willReturn(Optional.of(user));

        User foundUser = userService.find("test1");

        assertEquals("test1", foundUser.getUserId());
    }

    @DisplayName("등록되어 있지 않는 사용자")
    @Test
    void testFindInvalidUser() {
        BDDMockito.given(userRepository.findByUserId(any()))
                .willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.find("test1"));
    }
}
