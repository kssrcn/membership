package com.example.kakaopay_membership.membership.repository;

import com.example.kakaopay_membership.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

}
