package com.lucky.finpay.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 객체 생성시 안전성 보장받음
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

    @Column
    private String payLink;

    @Builder
    public User(String name, String email, String profileImgUrl) {
        this.name = name;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }

    public User update(String name, String profileImgUrl) {
        this.name = name;
        this.profileImgUrl = profileImgUrl;

        return this;
    }
}
