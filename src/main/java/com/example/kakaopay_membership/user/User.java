package com.example.kakaopay_membership.user;

import com.example.kakaopay_membership.membership.Membership;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Membership> memberships = new ArrayList<>();

    public User(String userId) {
        this.userId = userId;
    }

    public void addMemberShip(Membership memberShip) {
        memberships.add(memberShip);
    }
}
