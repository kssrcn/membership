package com.example.membership.api;

import com.example.membership.membership.entity.Membership;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MembershipFindAllResponseDto {
    private boolean success;
    private List<MembershipFindResponseDto> response = new ArrayList<>();
    private ErrorDto error;

    private MembershipFindAllResponseDto() {}

    public static MembershipFindAllResponseDto ofSuccess(List<Membership> memberships) {
        MembershipFindAllResponseDto membershipFindAllResponseDto = new MembershipFindAllResponseDto();
        membershipFindAllResponseDto.addMembershipFindResponseDtos(memberships);
        membershipFindAllResponseDto.success = true;
        return membershipFindAllResponseDto;
    }

    private void addMembershipFindResponseDtos(List<Membership> memberships) {
        for (int seq = 0; seq < memberships.size(); seq++)
            addMembershipFindResponseDto(seq, memberships.get(seq));
    }

    private void addMembershipFindResponseDto(int seq, Membership membership) {
        response.add(new MembershipFindResponseDto(seq, membership));
    }

    public static MembershipFindAllResponseDto ofError(RuntimeException error) {
        MembershipFindAllResponseDto membershipFindAllResponseDto = new MembershipFindAllResponseDto();
        membershipFindAllResponseDto.error = new ErrorDto(error);
        return membershipFindAllResponseDto;
    }

}
