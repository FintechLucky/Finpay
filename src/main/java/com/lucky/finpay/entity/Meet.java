package com.lucky.finpay.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Meet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Builder
    public Meet(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "meet")
    private List<MeetHasUser> meetHasUserList = new ArrayList<>();


}
