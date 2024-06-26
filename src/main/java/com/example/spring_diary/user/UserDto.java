package com.example.spring_diary.user;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {

    private long userId;
    private String nickname;
    private String email;
    private String password;
    private LocalDate birthday;
    private Role role;

}
