package com.example.kakaopay_membership.membership.service;

import com.example.kakaopay_membership.membership.dto.MembershipAddPointRequestDto;
import com.example.kakaopay_membership.membership.entity.Membership;
import com.example.kakaopay_membership.membership.entity.MembershipStatus;
import com.example.kakaopay_membership.membership.entity.MembershipType;
import com.example.kakaopay_membership.membership.dto.MembershipFindDto;
import com.example.kakaopay_membership.membership.dto.MembershipRegistrationDto;
import com.example.kakaopay_membership.membership.repository.MembershipRepository;
import com.example.kakaopay_membership.user.entity.User;
import com.example.kakaopay_membership.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipServiceImpl implements MembershipService {
    private final UserService userService;
    private final MembershipRepository memberShipRepository;


    @Override
    @Transactional
    public Membership register(MembershipRegistrationDto membershipRegistrationDto) {
        User user = userService.find(membershipRegistrationDto.getUserId());
        Membership memberShip = makeMembership(user, membershipRegistrationDto.getMembershipId(), membershipRegistrationDto.getPoint());
        memberShipRepository.save(memberShip);
        return memberShip;
    }

    @Override
    @Transactional
    public void inactivate(MembershipFindDto membershipFindDto) {
        User user = userService.find(membershipFindDto.getUserId());
        user.inactivateMembership(membershipFindDto.getMembershipId());
    }

    @Override
    public Membership find(MembershipFindDto membershipFindDto) {
        User user = userService.find(membershipFindDto.getUserId());
        return user.findMembership(membershipFindDto.getMembershipId());
    }

    @Override
    @Transactional
    public void addPoint(MembershipAddPointRequestDto membershipAddPointRequestDto) {
        User user = userService.find(membershipAddPointRequestDto.getUserId());
        user.addPointToMembership(membershipAddPointRequestDto.getMembershipId(), membershipAddPointRequestDto.getPoint());
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
