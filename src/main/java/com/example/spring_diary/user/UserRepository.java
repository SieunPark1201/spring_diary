package com.example.spring_diary.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(long userId);

    User findByEmail(String email);

    User findByNickname(String username);

    boolean existsByEmail(String email);
}
