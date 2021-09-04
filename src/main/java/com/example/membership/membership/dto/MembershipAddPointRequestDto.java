package com.example.membership.membership.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MembershipAddPointRequestDto {
    private String userId;
    private String membershipId;
    private Long point;

    @Builder
    public MembershipAddPointRequestDto(String membershipId, Long point) {
        this.membershipId = membershipId;
        this.point = point;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
