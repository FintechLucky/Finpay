package com.lucky.finpay.controller;

import com.lucky.finpay.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String login(Model model) {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) { // session 에 사용자가 있으면
            model.addAttribute("userName", user.getName());

            System.out.println();
            return "user/home";
        }

        // session 에 사용자가 없으면
        return "redirect:/login";
    }

    @GetMapping(value="/user/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
