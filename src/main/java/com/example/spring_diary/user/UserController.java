package com.example.spring_diary.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

//    로그인화면
    @GetMapping("user/login")
    public String userLogin() { return "user/login";}


}
