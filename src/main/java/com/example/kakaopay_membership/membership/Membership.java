package com.example.kakaopay_membership.membership;

import com.example.kakaopay_membership.user.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private MembershipType membershipType;
    private LocalDateTime startDate;
    private MembershipStatus membershipStatus;
    private Long point;

    @Builder
    public Membership(MembershipType membershipType, LocalDateTime startDate, MembershipStatus membershipStatus, Long point) {
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.membershipStatus = membershipStatus;
        this.point = point;
    }

    public void registerUser(User user) {
        this.user = user;
        user.addMemberShip(this);
    }

}
