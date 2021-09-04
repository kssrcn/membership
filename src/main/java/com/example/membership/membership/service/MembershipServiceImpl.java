package com.example.membership.membership.service;

import com.example.membership.membership.dto.MembershipAddPointRequestDto;
import com.example.membership.membership.entity.Membership;
import com.example.membership.membership.entity.MembershipStatus;
import com.example.membership.membership.entity.MembershipType;
import com.example.membership.membership.dto.MembershipFindDto;
import com.example.membership.membership.dto.MembershipRegistrationDto;
import com.example.membership.membership.repository.MembershipRepository;
import com.example.membership.user.entity.User;
import com.example.membership.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipServiceImpl implements MembershipService {
    private final UserService userService;
    private final MembershipRepository memberShipRepository;


    @Override
    public List<Membership> findAllForUser(String userId) {
        User user = userService.find(userId);
        return user.getMemberships();
    }

    @Override
    @Transactional
    public Membership register(MembershipRegistrationDto membershipRegistrationDto) {
        User user = userService.find(membershipRegistrationDto.getUserId());
        Membership memberShip = makeMembership(membershipRegistrationDto.getMembershipId(), membershipRegistrationDto.getPoint());
        memberShip.registerUser(user);
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
    public Membership findForUser(MembershipFindDto membershipFindDto) {
        User user = userService.find(membershipFindDto.getUserId());
        return user.findMembership(membershipFindDto.getMembershipId());
    }

    @Override
    @Transactional
    public void addPoint(MembershipAddPointRequestDto membershipAddPointRequestDto) {
        User user = userService.find(membershipAddPointRequestDto.getUserId());
        user.addPointToMembership(membershipAddPointRequestDto.getMembershipId(), membershipAddPointRequestDto.getPoint());
    }

    private Membership makeMembership(String memberShipId, Long point) {
        MembershipType memberShipType = MembershipType.findByMemberShipId(memberShipId);
        Membership memberShip = createMembership(memberShipType, point);
        return memberShip;
    }

    private Membership createMembership(MembershipType memberShipType, Long point) {
        Membership memberShip = Membership.builder()
                .membershipType(memberShipType)
                .membershipStatus(MembershipStatus.Y)
                .startDate(LocalDateTime.now())
                .point(point)
                .build();
        return memberShip;
    }
}
