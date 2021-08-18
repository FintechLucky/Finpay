package com.lucky.finpay.controller;

import com.lucky.finpay.dto.SessionUser;
import com.lucky.finpay.service.KakaoOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final KakaoOAuth2UserService kakaoOAuth2UserService;
    private final HttpSession httpSession;

    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    private final ClientRegistrationRepository clientRegistrationRepository;

    @CrossOrigin
    @GetMapping("/")
    public String login(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) { // session 에 사용자가 있으면
            model.addAttribute("userName", user.getName());
            if (!kakaoOAuth2UserService.chkPayLink(user.getEmail())) {
                return "user/qrCodeForm";
            }
            return "user/home";
        }

        // session 에 사용자가 없으면
        return "redirect:/login";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/login")
    public Map<String,String> getLoginPage() {
        Map<String, String> result = new HashMap<>();
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        assert clientRegistrations != null;
        clientRegistrations.forEach(registration -> {
            result.put("url", authorizationRequestBaseUri + "/" + registration.getRegistrationId());
        });
        return result;
    }

    @CrossOrigin
    @GetMapping(value = "/user/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
