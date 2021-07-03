package com.example.kakaopay_membership.exception;

public class MembershipTypeNotMatch extends RuntimeException {
    public MembershipTypeNotMatch(String message) {
        super(message);
    }
}
