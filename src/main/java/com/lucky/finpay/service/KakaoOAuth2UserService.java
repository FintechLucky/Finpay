package com.lucky.finpay.service;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.lucky.finpay.dto.OAuthAttributes;
import com.lucky.finpay.dto.SessionUser;
import com.lucky.finpay.entity.User;
import com.lucky.finpay.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 로그인 진행 시 키가 되는 필드 값 (PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.ofKakao(userNameAttributeName, oAuth2User.getAttributes());

        // DB 에 해당 데이터 Update or Save
        User user = saveOrUpdate(attributes);

        // Session 에 해당 사용자 데이터 추가
        httpSession.setAttribute("user",new SessionUser(user)); //SessionUser (직렬화 된 dto 사용)

        return new DefaultOAuth2User( Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey() );
    }

    // DB 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getProfileImgUrl()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }

    // payLinke check 서비스 로직
    public boolean chkPayLink(String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        if (user.getPayLink() == null) {
            return false;
        }
        return true;
    }

    // payLink Update 서비스 로직
    public User updatePayLink(String payLink, String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        user.setPayLink(payLink);
        return userRepository.save(user);
    }
}