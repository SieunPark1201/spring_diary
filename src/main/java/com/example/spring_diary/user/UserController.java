package com.example.spring_diary.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // 사용자 업데이트 화면으로 이동
    @GetMapping("/user/update")
    public String showUpdateForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();

        // User를 UserDto로 변환하여 전달
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setNickname(user.getNickname());
        userDto.setEmail(user.getEmail());
        userDto.setBirthday(user.getBirthday());

        model.addAttribute("userDto", userDto);
        return "user/user-update";
    }

    // 사용자 업데이트 요청 처리
    @PostMapping("/user/update")
    public String updateUser(UserDto userDto, Model model) throws Exception {
        try {
            // 현재 사용자의 ID를 userDto에 설정하여 업데이트
            User currentUser = userService.getCurrentUser();
            userDto.setUserId(currentUser.getUserId());

            userService.updateUser(userDto);
            return "redirect:/home"; // 업데이트 후 홈 페이지로 리다이렉션
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보를 업데이트하는 중 오류가 발생했습니다.");
            return "user/user-update"; // 에러가 발생한 경우 다시 업데이트 페이지로
        }
    }

    // 회원탈퇴 화면
    @PostMapping("user/delete")
    public String userDelete(UserDto userDto) throws Exception{
        userService.deleteUser(userDto);
        return "redirect:/";
    }



}
