package com.example.kakaopay_membership.membership.entity;

import com.example.kakaopay_membership.user.entity.User;
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

    protected Membership() {

    }

    @Builder
    public Membership(MembershipType membershipType, LocalDateTime startDate, MembershipStatus membershipStatus, Long point) {
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.membershipStatus = membershipStatus;
        this.point = point;
    }

    public void registerUser(User user) {
        if(user!=null) {
            this.user = user;
            user.addMemberShip(this);
        }
    }

    public void inactivate() {
        membershipStatus = MembershipStatus.N;
    }

    public void addPoint(Long point) {
        this.point += point;
    }
}
