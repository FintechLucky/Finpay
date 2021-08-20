package com.lucky.finpay.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class PayMeet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    // 정산 상태 - 진행 중 / 완료
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "payMeet")
    private List<PayMeetHasUser> payMeetHasUserList = new ArrayList<>();

    // 모임의 상태가 완료될 시

}
