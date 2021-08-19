package com.lucky.finpay.service;

import com.google.gson.Gson;
import com.lucky.finpay.dto.AuthorizationKakao;
import com.lucky.finpay.entity.User;
import com.lucky.finpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Oauth2Kakao oauth2Kakao;
    private final UserRepository userRepository;
    private AuthorizationKakao authorizationKakao;

    private Gson gson = new Gson();


    // 카카오로부터 사용자 정보 및 AccessToken 받아오기
    public Map<String, Object> oauth2AuthorizationKakao(String code) {
        Map<String, Object> result = new HashMap<>();
        authorizationKakao = oauth2Kakao.callTokenApi(code);
        String userInfoFromKakao = oauth2Kakao.callGetUserByAccessToken(authorizationKakao.getAccess_token());
        System.out.println("userInfoFromKakao = " + userInfoFromKakao);
        result.put("user", saveOrUpdate((userInfoFromKakao)));
        result.put("accessToken", authorizationKakao.getAccess_token()); // authorizationKakao 전달 시 더 많은 token 정보 활용 가능
        return result;
    }

    // DB 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(String userInfoFromKakao) {
        Map<String,Object> attributes = gson.fromJson(userInfoFromKakao, Map.class);

        // Kakao는 kakao_account 에 유저정보가 있다. eamil
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        String name = (String) kakaoProfile.get("nickname");
        String email = (String) kakaoAccount.get("email");
        String profileImgUrl = (String) kakaoProfile.get("profile_image_url");

        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name, profileImgUrl))
                .orElse(new User(name, email, profileImgUrl));
        return userRepository.save(user);
    }

    // payLink Update 서비스 로직 - 아직 필요 없음
    public User updatePayLink(String payLink, String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        user.setPayLink(payLink);
        return userRepository.save(user);
    }

}
