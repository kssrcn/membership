package com.example.kakaopay_membership.membership;

import com.example.kakaopay_membership.exception.UserNotFoundException;
import com.example.kakaopay_membership.user.User;
import com.example.kakaopay_membership.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipServiceImpl implements MembershipService {
    private final UserRepository userRepository;
    private final MembershipRepository memberShipRepository;


    @Override
    @Transactional
    public void register(MembershipRegistrationDto memberShipRegistrationDto) {
        User user = findUser(memberShipRegistrationDto.getUserId());
        Membership memberShip = makeMembership(user, memberShipRegistrationDto.getMembershipId(), memberShipRegistrationDto.getPoint());
        memberShipRepository.save(memberShip);
    }

    private User findUser(String userId) {
        Optional<User> selectedUser = userRepository.findByUserId(userId);
        User user = selectedUser.orElseThrow(() -> new UserNotFoundException("사용자를 찾을수 없습니다."));
        return user;
    }

    private Membership makeMembership(User user, String memberShipId, Long point) {
        MembershipType memberShipType = MembershipType.findByMemberShipId(memberShipId);
        Membership memberShip = createMembership(user, memberShipType, point);
        return memberShip;
    }

    private Membership createMembership(User user, MembershipType memberShipType, Long point) {
        Membership memberShip = Membership.builder()
                .membershipType(memberShipType)
                .membershipStatus(MembershipStatus.Y)
                .startDate(LocalDateTime.now())
                .point(point)
                .build();
        memberShip.registerUser(user);
        return memberShip;
    }
}
