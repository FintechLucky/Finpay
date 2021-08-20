package com.lucky.finpay.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@Table(name = "payMeet_has_user")
public class PayMeetHasUser implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "payMeet_id")
    private PayMeet payMeet;


}
