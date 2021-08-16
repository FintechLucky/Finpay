package com.lucky.finpay.config;

import com.lucky.finpay.service.KakaoOAuth2UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final KakaoOAuth2UserService kakaoOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .httpBasic()
                .and()
                    .authorizeRequests()
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/**", "/static/**").permitAll()
                .and()
                    .logout()
                        .logoutUrl("/user/logout.do")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                .and()
                    .oauth2Login()
                        .userInfoEndpoint().userService(kakaoOAuth2UserService)
                    .and()
                        .loginPage("/login");

        http.csrf().disable();
    }

}