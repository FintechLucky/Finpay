package com.lucky.finpay.controller;

import com.lucky.finpay.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MeetController {
    private final MeetService meetService;

    /**
     * 모임 생성
     * param emailListObject - 멤버 사용자들 이메일, name - 모임 이름
     * return
     */
    @PostMapping("/meet/save")
    @ResponseBody
    public Map<String, Object> save(@RequestBody Map<String,List<String>> emailListObject, @RequestParam String name) {
        List<String> emailList = emailListObject.get("emailList");
        return meetService.save(emailList, name);
    }
}
