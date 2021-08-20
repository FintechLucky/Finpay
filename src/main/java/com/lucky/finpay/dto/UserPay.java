package com.lucky.finpay.dto;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
public class UserPay {

    private String email;
    private String payLink;

    @Builder
    public UserPay(String email, String payLink) {
        this.email = email;
        this.payLink = payLink;
    }

}
