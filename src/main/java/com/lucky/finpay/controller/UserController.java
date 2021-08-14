package com.lucky.finpay.controller;

import com.google.gson.Gson;
import com.lucky.finpay.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final HttpSession httpSession;
    Gson gson = new Gson();

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @GetMapping("/user/list")
    public String friend_list() {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        String auth = authorizationUri;
        auth += "?client_id=" + clientId;
        auth += "&redirect_uri=" + "http://15.165.237.166:8080/auth";
//        auth += "&redirect_uri=" + "http://localhost:8080/auth";
        auth += "&response_type=" + "code";
        return "redirect:" + auth;
    }

    @GetMapping("/auth")
    public String get_token(@RequestParam String code) {
        String result = "";

        String strToken = tokenUri;
        strToken += "?grant_type=" + "authorization_code";
        strToken += "&client_id=" + clientId;
        strToken += "&redirect_uri=" + "http://15.165.237.166:8080/auth";
//        strToken += "&redirect_uri=" + "http://localhost:8080/auth";
        strToken += "&code=" + code;

        try {
            URL token = new URL(strToken);
            HttpURLConnection con = (HttpURLConnection) token.openConnection();
            con.setRequestMethod("POST");

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                result = sb.toString();
            } else {
                System.out.println(con.getResponseMessage());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        // 결과 json parsing 처리
        Map map = gson.fromJson(result, Map.class);

        return "redirect:/user/friends?access_token="+map.get("access_token");
    }

    @GetMapping("/user/friends")
    @ResponseBody
    public String friends(@RequestParam String access_token) {
        String result = "";

        String friendsUrl = "https://kapi.kakao.com/v1/api/talk/friends";

        try {
            URL friends = new URL(friendsUrl);
            HttpURLConnection con2 = (HttpURLConnection) friends.openConnection();
            con2.setRequestProperty("Authorization", "Bearer "+access_token);
            con2.setRequestMethod("GET");

            StringBuilder sb = new StringBuilder();
            if (con2.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("test");
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con2.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                result = sb.toString();

            } else {
                System.out.println(con2.getResponseMessage());
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // 결과 json parsing 처리
        // Map map = gson.fromJson(result, Map.class);

        return result;
    }
}
