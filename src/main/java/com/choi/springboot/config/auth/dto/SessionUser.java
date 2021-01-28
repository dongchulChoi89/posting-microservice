package com.choi.springboot.config.auth.dto;

import com.choi.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// we do not use User class because in here we need serialization function, but User class is entity class, we do not implement serialization in the entity class
// because of performance issues
@Getter
public class SessionUser implements Serializable {
    // only need info used for session, others we don't need
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
