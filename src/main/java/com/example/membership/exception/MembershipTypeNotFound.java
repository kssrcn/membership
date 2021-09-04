package com.example.membership.exception;

public class MembershipTypeNotFound extends RuntimeException {
    public MembershipTypeNotFound(String message) {
        super(message);
    }
}
