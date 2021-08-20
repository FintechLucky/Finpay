package com.lucky.finpay.repository;

import com.lucky.finpay.dto.UserPay;
import com.lucky.finpay.entity.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {

    @Query("select new com.lucky.finpay.dto.UserPay(u.email, u.payLink) from User u where u.id = any(select mhu.user.id from MeetHasUser mhu where mhu.meet.id = :meetId)")
    List<UserPay> findUserByMeetId(@Param("meetId") Long id);
}
