package com.example.membership.api;

import com.example.membership.membership.entity.Membership;
import com.example.membership.membership.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MembershipApiController {

    private final MembershipService membershipService;

    @GetMapping("membership")
    public MembershipFindAllResponseDto membershipFindAll(@RequestHeader(value = "X-USER-ID") String userId) {
        try {
            List<Membership> allForUser = membershipService.findAllForUser(userId);
            return MembershipFindAllResponseDto.ofSuccess(allForUser);
        } catch (RuntimeException error) {
            return MembershipFindAllResponseDto.ofError(error);
        }
    }
}
