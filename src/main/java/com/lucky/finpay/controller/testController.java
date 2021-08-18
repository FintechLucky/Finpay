package com.lucky.finpay.controller;

import com.lucky.finpay.entity.Role;
import com.lucky.finpay.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
@ResponseBody
public class testController {
    @GetMapping("/test")
    public User test() {
        User user = new User("name","email","profileImgUrl",Role.USER);
        return user;
    }

}