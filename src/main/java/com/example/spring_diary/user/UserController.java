package com.example.spring_diary.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

//    로그인화면
    @GetMapping("user/login")
    public String userLogin() { return "user/login";}


//    회원가입 화면
    @GetMapping("user/new")
    public String newUser(){
        return "user/user-register";
    }

    @PostMapping("user/new")
    public String newUser(UserDto userDto){
        userService.createUser(userDto);

    }



}
