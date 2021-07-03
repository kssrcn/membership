package com.example.kakaopay_membership.membership.entity;

import com.example.kakaopay_membership.exception.MembershipTypeNotFound;

public enum MembershipType {
    SPC("spc","happypoint"),
    SHINSEGAE("shinsegae","shinsegaepoint"),
    CJ("cj","cjone");

    private final String memberShipId;
    private final String memberShipName;

    MembershipType(String memberShipId, String memberShipName) {
        this.memberShipId = memberShipId;
        this.memberShipName = memberShipName;
    }

    public static MembershipType findByMemberShipId(String memberShipId) {
        switch (memberShipId) {
            case "spc" :
                return MembershipType.SPC;
            case "shinsegae" :
                return MembershipType.SHINSEGAE;
            case "cj" :
                return MembershipType.CJ;
            default :
                throw new MembershipTypeNotFound("맴버십 아이디를 잘못 입력했습니다.");
        }
    }

    public String getMemberShipId() {
        return memberShipId;
    }

    public String getMemberShipName() {
        return memberShipName;
    }
}
