package com.example.membership.membership.repository;

import com.example.membership.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

}
