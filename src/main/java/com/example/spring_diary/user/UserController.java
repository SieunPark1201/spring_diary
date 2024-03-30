package com.example.spring_diary.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

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
        return "redirect:/";
    }


    // 회원수정 화면
    @PostMapping("user/update")
    public String userUpdate(UserDto userDto) throws Exception{
        userService.updateUser(userDto);
        return "redirect:/";
    }

    // 회원삭제 화면
    @GetMapping("user/delete")
    public String userDelete(UserDto userDto) throws Exception{
        userService.deleteUser(userDto);
        return "redirect:/";
    }



}
