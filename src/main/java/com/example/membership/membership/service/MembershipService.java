package com.example.membership.membership.service;

import com.example.membership.membership.dto.MembershipAddPointRequestDto;
import com.example.membership.membership.entity.Membership;
import com.example.membership.membership.dto.MembershipFindDto;
import com.example.membership.membership.dto.MembershipRegistrationDto;

import java.util.List;

public interface MembershipService {
    Membership register(MembershipRegistrationDto membershipRegistrationDto);

    void inactivate(MembershipFindDto membershipFindDto);

    Membership findForUser(MembershipFindDto membershipFindDto);

    void addPoint(MembershipAddPointRequestDto membershipAddPointRequestDto);

    List<Membership> findAllForUser(String userId);
}
