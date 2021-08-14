package com.lucky.finpay.dto;

import com.lucky.finpay.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String profileImgUrl;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
