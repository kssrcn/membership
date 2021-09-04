package com.example.membership.api;

import com.example.membership.membership.entity.Membership;
import com.example.membership.membership.entity.MembershipStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MembershipFindResponseDto {
    private int seq;
    private String membershipId;
    private String userId;
    private String membershipName;
    private LocalDateTime startDate;
    private MembershipStatus membershipStatus;
    private Long point;

    public MembershipFindResponseDto(int seq, Membership membership) {
        this.seq = seq;
        this.membershipId = membership.getMembershipType().getMemberShipId();
        this.userId = membership.getUser().getUserId();
        this.membershipName = membership.getMembershipType().getMemberShipName();
        this.startDate = membership.getStartDate();
        this.membershipStatus = membership.getMembershipStatus();
        this.point = membership.getPoint();
    }
}
