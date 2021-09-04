package com.example.membership.membership.dto;

import lombok.Getter;

@Getter
public class MembershipRegistrationDto {
    private String userId;
    private String membershipId;
    private String membershipName;
    private Long point;

    public MembershipRegistrationDto(String membershipId, String membershipName, Long point) {
        this.membershipId = membershipId;
        this.membershipName = membershipName;
        this.point = point;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
