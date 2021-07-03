package com.example.kakaopay_membership.membership.service;

import com.example.kakaopay_membership.membership.dto.MembershipAddPointRequestDto;
import com.example.kakaopay_membership.membership.entity.Membership;
import com.example.kakaopay_membership.membership.dto.MembershipFindDto;
import com.example.kakaopay_membership.membership.dto.MembershipRegistrationDto;

public interface MembershipService {
    Membership register(MembershipRegistrationDto membershipRegistrationDto);

    void inactivate(MembershipFindDto membershipFindDto);

    Membership find(MembershipFindDto membershipFindDto);

    void addPoint(MembershipAddPointRequestDto membershipAddPointRequestDto);
}
