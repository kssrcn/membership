package com.example.membership.api;

import com.example.membership.membership.entity.Membership;
import com.example.membership.membership.entity.MembershipStatus;
import com.example.membership.membership.entity.MembershipType;
import com.example.membership.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MembershipFindAllResponseDtoTest {

    @DisplayName("성공시 Dto 객체 생성")
    @Test
    void testOfSuccess() {
        User user = new User("test1");
        Membership membership1 = makeMembership(user, MembershipType.CJ, MembershipStatus.Y);
        Membership membership2 = makeMembership(user, MembershipType.SPC, MembershipStatus.N);
        Membership membership3 = makeMembership(user, MembershipType.SHINSEGAE, MembershipStatus.N);
        List<Membership> memberships = Arrays.asList(membership1, membership2, membership3);

        MembershipFindAllResponseDto membershipFindAllResponseDto = MembershipFindAllResponseDto.ofSuccess(memberships);
        List<MembershipFindResponseDto> response = membershipFindAllResponseDto.getResponse();

        assertMembership(MembershipType.CJ, MembershipStatus.Y,response.get(0));
        assertMembership(MembershipType.SPC, MembershipStatus.N,response.get(1));
        assertMembership(MembershipType.SHINSEGAE, MembershipStatus.N, response.get(2));
        assertTrue(membershipFindAllResponseDto.isSuccess());
        assertNull(membershipFindAllResponseDto.getError());
    }

    @DisplayName("실패시 Dto 객체 생성")
    @Test
    void testOfError() {

    }

    private void assertMembership(MembershipType expectedMembershipType, MembershipStatus expectedMembershipStatus,
                                  MembershipFindResponseDto response) {
        assertEquals(expectedMembershipType.getMemberShipId(), response.getMembershipId());
        assertEquals(expectedMembershipType.getMemberShipName(), response.getMembershipName());
        assertEquals(expectedMembershipStatus, response.getMembershipStatus());
    }

    private Membership makeMembership(User user, MembershipType membershipType, MembershipStatus membershipStatus) {
        Membership membership = Membership.builder()
                .membershipType(membershipType)
                .membershipStatus(membershipStatus)
                .build();
        membership.registerUser(user);
        return membership;
    }

}