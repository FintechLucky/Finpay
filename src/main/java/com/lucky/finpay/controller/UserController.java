package com.lucky.finpay.controller;

import com.lucky.finpay.entity.User;
import com.lucky.finpay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    /**
     * Kakao 로그인 처리
     * param AuthorizationCode
     * return User 객체 및 AccessToken
     */
    @ResponseBody
    @GetMapping("/oauth2/authorization/kakao")
    public Map<String, Object> oauth2AuthorizationKakao(@RequestParam("code") String code) {
        return userService.oauth2AuthorizationKakao(code);
    }

    /**
     * 송금 링크 등록하기
     * param body {payLink, email}
     * return User 객체
     */
    @ResponseBody
    @PostMapping("/user/qrCode")
    public User qrCode(@RequestBody Map<String, String> body) {
        String payLink = body.get("payLink");
        String email = body.get("email");
        return userService.updatePayLink(payLink, email);
    }

}
