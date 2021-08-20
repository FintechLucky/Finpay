package com.lucky.finpay.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@Table(name = "meet_has_user")
public class MeetHasUser implements Serializable {
    //Serializable ? 직렬화 - 자바 내부에서 사용되는 객체를 외부에서도 사용할 수 있도록 바이트 형태로 데이터 변환

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "meet_id")
    private Meet meet;

}
