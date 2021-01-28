package com.choi.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// take charge of control the authorization of the user
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "Guest"), // in Spring Security framework, authorization code needs 'ROLE_' in front of the code name
    USER("ROLE_USER", "User");

    private final String key;
    private final String title;
}
