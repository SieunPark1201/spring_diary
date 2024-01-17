package com.example.spring_diary.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 20)
    private String nickname;

    @Column(length = 50)
    private String email;

    @Column(length = 20)
    private String password;

    @Column
    private LocalDate birthday;


    public user(String nickname, String email, String password, LocalDate birthday){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }
}
