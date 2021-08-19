package com.lucky.finpay.repository;

import com.lucky.finpay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // 이미 E-mail 을 통해 생성된 사용자인지 체크
}
