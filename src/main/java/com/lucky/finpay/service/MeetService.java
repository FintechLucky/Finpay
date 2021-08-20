package com.lucky.finpay.service;

import com.lucky.finpay.dto.UserPay;
import com.lucky.finpay.entity.Meet;
import com.lucky.finpay.entity.MeetHasUser;
import com.lucky.finpay.entity.User;
import com.lucky.finpay.repository.MeetRepository;
import com.lucky.finpay.repository.PayMeetRepository;
import com.lucky.finpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MeetService {
    private final MeetRepository meetRepository;
    private final UserRepository userRepository;

    /**
     * 모임 생성
     * param : 멤버 사용자들 이메일, 모임 이름
     */
    public Map<String , Object> save(@NotNull List<String> emailList, String name) {
        // 모임 생성
        Meet meet = new Meet(name);

        // 사용자 추가
        for (String email : emailList) {
            MeetHasUser meetHasUser = new MeetHasUser();

            User user = userRepository.findByEmail(email).get();

            meetHasUser.setUser(user);
            meetHasUser.setMeet(meet);
            user.addMeetHasUser(meetHasUser);

        }

        Meet savedMeet = meetRepository.save(meet);
        List<UserPay> meetUserList = meetRepository.findUserByMeetId(savedMeet.getId());
        // 결과 생성 - {모임 이름 , 사용자 email}
        Map<String, Object> result = new HashMap<>();
        result.put("name", savedMeet.getName());
        result.put("userList", meetUserList);

        return result;
    }



    /**
     * 모임 삭제
     */


}
