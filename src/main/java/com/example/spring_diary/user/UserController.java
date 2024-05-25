package com.example.spring_diary.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public UserController(UserService userService){
        this.userService = userService;
    }

//    로그인화면
@PostMapping("/user/login")
    public String userLogin(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        String errorMessage = null;
        if (request.getParameter("error") != null) {
            errorMessage = request.getParameter("exception");
            errorMessage = URLDecoder.decode(errorMessage, StandardCharsets.UTF_8.name());
        }
        model.addAttribute("errorMessage", errorMessage);
        return "user/login";}

    @GetMapping("/user/login")
    public String showLoginPage() {
        return "user/login";
    }

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

    // 회원탈퇴 화면
    @PostMapping("user/delete")
    public String userDelete(UserDto userDto) throws Exception{
        userService.deleteUser(userDto);
        return "redirect:/";
    }



}
