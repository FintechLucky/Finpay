package com.lucky.finpay.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "oAuth2Id", "email", "nickname", "introduction", "role"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String profileImgUrl;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public User(String name, String email, String profileImgUrl, Role role) {
        this.name = name;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.role = role;
    }

    public User update(String name, String profileImgUrl) {
        this.name = name;
        this.profileImgUrl = profileImgUrl;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
