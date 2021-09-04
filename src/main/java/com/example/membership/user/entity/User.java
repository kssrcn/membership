package com.example.membership.user.entity;

import com.example.membership.exception.MembershipTypeNotFound;
import com.example.membership.membership.entity.Membership;
import com.example.membership.membership.entity.MembershipType;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Membership> memberships = new ArrayList<>();

    protected User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public void addMemberShip(Membership memberShip) {
        if(!memberships.contains(memberShip)) {
            memberships.add(memberShip);
            memberShip.registerUser(this);
        }
    }

    public void inactivateMembership(String membershipId) {
        Membership membership = findMembership(membershipId);
        membership.inactivate();
    }

    public void addPointToMembership(String membershipId, Long point) {
        Membership membership = findMembership(membershipId);
        membership.addPoint(point);
    }

    public Membership findMembership(String membershipId) {
        MembershipType membershipType = MembershipType.findByMemberShipId(membershipId);
        return findMembershipByMembershipType(membershipType);
    }

    private Membership findMembershipByMembershipType(final MembershipType membershipType) {
        Optional<Membership> selectedMembership = memberships.stream()
                .filter(membership -> membershipType == membership.getMembershipType())
                .findFirst();
        Membership membership = selectedMembership.orElseThrow(() -> new MembershipTypeNotFound(userId + "는 " + membershipType + "가 없습니다."));
        return membership;
    }
}
