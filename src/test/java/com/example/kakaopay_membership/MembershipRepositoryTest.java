package com.example.kakaopay_membership;

import com.example.kakaopay_membership.membership.entity.Membership;
import com.example.kakaopay_membership.membership.repository.MembershipRepository;
import com.example.kakaopay_membership.membership.entity.MembershipStatus;
import com.example.kakaopay_membership.membership.entity.MembershipType;
import com.example.kakaopay_membership.user.entity.User;
import com.example.kakaopay_membership.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class MembershipRepositoryTest {

    @Autowired
    MembershipRepository memberShipRepository;
    @Autowired
    UserRepository userRepository;

    @DisplayName("MemberShip 조회하기")
    @Test
    void testSelect() {
        Membership memberShip = Membership.builder()
                .membershipType(MembershipType.SPC)
                .membershipStatus(MembershipStatus.Y)
                .startDate(LocalDateTime.of(2021, 6, 30, 13, 20, 33, 832))
                .point(400L)
                .build();

        User user = new User("kim");
        memberShip.registerUser(user);
        userRepository.save(user);
        memberShipRepository.save(memberShip);
        Membership selectedMembership = memberShipRepository.findById(memberShip.getId()).get();
        assertEquals("kim", selectedMembership.getUser().getUserId());
        assertEquals(MembershipType.SPC, selectedMembership.getMembershipType());
        assertEquals(MembershipStatus.Y, selectedMembership.getMembershipStatus());
        assertEquals(LocalDateTime.of(2021, 6, 30, 13, 20, 33, 832),
                selectedMembership.getStartDate());
        assertEquals(400, selectedMembership.getPoint());
    }
}
