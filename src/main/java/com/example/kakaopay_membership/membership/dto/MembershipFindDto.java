package com.example.kakaopay_membership.membership.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MembershipFindDto {
    private String userId;
    private String membershipId;

    @Builder
    public MembershipFindDto(String userId, String membershipId) {
        this.userId = userId;
        this.membershipId = membershipId;
    }
}
