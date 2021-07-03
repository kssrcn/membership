package com.example.kakaopay_membership;

import com.example.kakaopay_membership.exception.MembershipTypeNotMatch;
import com.example.kakaopay_membership.membership.*;
import com.example.kakaopay_membership.user.User;
import com.example.kakaopay_membership.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class MembershipServiceTest {

    MembershipService memberShipService;
    UserRepository userRepository;
    MembershipRepository memberShipRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        memberShipRepository = mock(MembershipRepository.class);
        memberShipService = new MembershipServiceImpl(userRepository, memberShipRepository);
    }

    @DisplayName("맴버십 등록")
    @Test
    void testRegisterMemberShip() {
        MembershipRegistrationDto memberShipRegistrationDto = new MembershipRegistrationDto("cj", "cjone", 500L);
        memberShipRegistrationDto.setUserId("test1");
        BDDMockito.given(userRepository.findByUserId(any()))
                .willReturn(Optional.of(new User("test1")));

        memberShipService.register(memberShipRegistrationDto);

        BDDMockito.then(memberShipRepository)
                .should()
                .save(any(Membership.class));
    }

    @DisplayName("존재하지 않는 맴버십 등록")
    @Test
    void testRegisterInValidMemberShip() {
        MembershipRegistrationDto memberShipRegistrationDto = new MembershipRegistrationDto("invalidMemberShip", "invalid", 500L);
        memberShipRegistrationDto.setUserId("test1");
        BDDMockito.given(userRepository.findByUserId(any()))
                .willReturn(Optional.of(new User("test1")));

        Assertions.assertThrows(MembershipTypeNotMatch.class, () -> memberShipService.register(memberShipRegistrationDto));
    }
}
