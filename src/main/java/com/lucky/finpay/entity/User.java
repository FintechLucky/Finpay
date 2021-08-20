package com.lucky.finpay.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 객체 생성시 안전성 보장받음
public class User implements Serializable {

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MeetHasUser> meetHasUserList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PayMeetHasUser> payMeetHasUserList = new ArrayList<>();

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

    // 사용자를 즐겨찾는 모임에 추가
    public void addMeetHasUser(MeetHasUser meetHasUser) {
        this.meetHasUserList.add(meetHasUser);
    }

    // 즐겨찾는 모임에서 사용자 삭제
    public void removeMeetHasUser(MeetHasUser meetHasUser) {
        this.meetHasUserList.remove(meetHasUser);
    }

}
