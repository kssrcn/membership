package com.example.membership;

import com.example.membership.exception.MembershipTypeNotFound;
import com.example.membership.membership.dto.MembershipAddPointRequestDto;
import com.example.membership.membership.dto.MembershipFindDto;
import com.example.membership.membership.dto.MembershipRegistrationDto;
import com.example.membership.membership.entity.Membership;
import com.example.membership.membership.entity.MembershipStatus;
import com.example.membership.membership.entity.MembershipType;
import com.example.membership.membership.repository.MembershipRepository;
import com.example.membership.membership.service.MembershipService;
import com.example.membership.membership.service.MembershipServiceImpl;
import com.example.membership.user.entity.User;
import com.example.membership.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MembershipServiceTest {

    MembershipService membershipService;
    UserService userService;
    MembershipRepository membershipRepository;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        membershipRepository = mock(MembershipRepository.class);
        membershipService = new MembershipServiceImpl(userService, membershipRepository);
    }

    @DisplayName("맴버십 등록")
    @Test
    void testRegisterMemberShip() {
        MembershipRegistrationDto memberShipRegistrationDto = new MembershipRegistrationDto("cj", "cjone", 500L);
        memberShipRegistrationDto.setUserId("test1");
        User user = new User("test1");
        BDDMockito.given(userService.find(any()))
                .willReturn(user);

        Membership registeredMembership = membershipService.register(memberShipRegistrationDto);

        assertEquals(MembershipType.CJ, registeredMembership.getMembershipType());
        assertEquals(MembershipStatus.Y, registeredMembership.getMembershipStatus());
        assertEquals(500L, registeredMembership.getPoint());
        assertEquals("test1", registeredMembership.getUser().getUserId());
    }

    @DisplayName("서비스 되지 않는 맴버십 등록")
    @Test
    void testRegisterInvalidMemberShip() {
        MembershipRegistrationDto memberShipRegistrationDto = new MembershipRegistrationDto("invalidMemberShip", "invalid", 500L);
        memberShipRegistrationDto.setUserId("test1");
        User user = new User("test1");
        BDDMockito.given(userService.find(any()))
                .willReturn(user);

        assertThrows(MembershipTypeNotFound.class, () -> membershipService.register(memberShipRegistrationDto));
    }

    @DisplayName("맴버십 비활성화 하기")
    @Test
    void testInActivateMemberShip() {
        MembershipFindDto membershipFindDto = MembershipFindDto.builder()
                .userId("test1")
                .membershipId("shinsegae")
                .build();
        User user = new User("test1");
        user.addMemberShip(makeMembershipByMembershipType(MembershipType.SHINSEGAE));
        BDDMockito.given(userService.find(any()))
                .willReturn(user);

        membershipService.inactivate(membershipFindDto);

        assertEquals(MembershipStatus.N,
                user.findMembership(membershipFindDto.getMembershipId()).getMembershipStatus());
    }

    @DisplayName("소유하고 있지 않는 맴버십 비활성화 하기")
    @Test
    void testInActivateInvalidMemberShip() {
        MembershipFindDto membershipFindDto = MembershipFindDto.builder()
                .userId("test1")
                .membershipId("shinsegae")
                .build();
        User user = new User("test1");
        BDDMockito.given(userService.find(any()))
                .willReturn(user);

        assertThrows(MembershipTypeNotFound.class, () -> membershipService.inactivate(membershipFindDto));
    }

    @DisplayName("맴버십 전체 조회")
    @Test
    void testFindAllMemberships() {
        User user = new User("test1");
        user.addMemberShip(makeMembershipByMembershipType(MembershipType.SPC));
        user.addMemberShip(makeMembershipByMembershipType(MembershipType.CJ));
        user.addMemberShip(makeMembershipByMembershipType(MembershipType.SHINSEGAE));
        BDDMockito.given(userService.find(any()))
                .willReturn(user);

        List<Membership> foundMemberships = membershipService.findAllForUser("test1");

        assertEquals("test1", foundMemberships.get(0).getUser().getUserId());
        assertEquals(MembershipType.SPC, foundMemberships.get(0).getMembershipType());
        assertEquals(MembershipStatus.Y, foundMemberships.get(0).getMembershipStatus());
        assertEquals("test1", foundMemberships.get(1).getUser().getUserId());
        assertEquals(MembershipType.CJ, foundMemberships.get(1).getMembershipType());
        assertEquals(MembershipStatus.Y, foundMemberships.get(1).getMembershipStatus());
        assertEquals("test1", foundMemberships.get(2).getUser().getUserId());
        assertEquals(MembershipType.SHINSEGAE, foundMemberships.get(2).getMembershipType());
        assertEquals(MembershipStatus.Y, foundMemberships.get(2).getMembershipStatus());
    }

    @DisplayName("맴버십 조회")
    @Test
    void testFindMembership() {
        MembershipFindDto membershipFindDto = MembershipFindDto.builder()
                .userId("test1")
                .membershipId("spc")
                .build();
        User user = new User("test1");
        user.addMemberShip(makeMembershipByMembershipType(MembershipType.SPC));
        BDDMockito.given(userService.find(any()))
                .willReturn(user);

        Membership foundMembership = membershipService.findForUser(membershipFindDto);

        assertEquals("test1", foundMembership.getUser().getUserId());
        assertEquals(MembershipType.SPC, foundMembership.getMembershipType());
        assertEquals(MembershipStatus.Y, foundMembership.getMembershipStatus());
    }

    @DisplayName("포인트 적립")
    @Test
    void testAddPoint() {
        MembershipAddPointRequestDto membershipAddPointRequestDto = new MembershipAddPointRequestDto("spc", 100L);
        membershipAddPointRequestDto.setUserId("test1");
        User user = new User("test1");
        user.addMemberShip(Membership.builder()
                .membershipType(MembershipType.SPC)
                .membershipStatus(MembershipStatus.Y)
                .point(130L)
                .build());
        BDDMockito.given(userService.find(any()))
                .willReturn(user);

        membershipService.addPoint(membershipAddPointRequestDto);

        assertEquals(230L, user.findMembership(membershipAddPointRequestDto.getMembershipId()).getPoint());

    }

    private Membership makeMembershipByMembershipType(MembershipType membershipType) {
        return Membership.builder()
                .membershipType(membershipType)
                .membershipStatus(MembershipStatus.Y)
                .build();
    }
}
