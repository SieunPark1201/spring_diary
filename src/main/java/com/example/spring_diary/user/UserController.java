package com.example.spring_diary.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String newUser(Model model){

        model.addAttribute("userDto", new UserDto()); // 빈 UserDto 객체를 모델에 추가


        return "user/user-register";
    }

    @PostMapping("/user/new")
    public String createUser(@ModelAttribute UserDto userDto, Model model) {
        try {
            // 이메일 중복 체크
            if (userService.isEmailTaken(userDto.getEmail(), null)) {
                model.addAttribute("errorMessage", "이 이메일은 이미 사용 중입니다.");
                return "user/user-register";
            }

            // 사용자 생성
            userService.createUser(userDto);
            return "redirect:/user/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
            return "user/user-register";
        }
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
            // 현재 사용자 정보 갱신
            updateSessionUser(userDto);

            return "redirect:/home"; // 업데이트 후 홈 페이지로 리다이렉션
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/user-update";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보를 업데이트하는 중 오류가 발생했습니다.");
            return "user/user-update"; // 에러가 발생한 경우 다시 업데이트 페이지로
        }
    }

    private void updateSessionUser(UserDto userDto) {
        // 현재 세션의 SecurityContext를 가져와서 사용자 정보를 갱신.
        User user = userService.findUserById(userDto.getUserId());

        // 새로운 UserPrincipal 생성
        UserPrincipal updatedUserPrincipal = new UserPrincipal(user);

        // SecurityContext의 Authentication 객체를 교체
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        updatedUserPrincipal,
                        updatedUserPrincipal.getPassword(),
                        updatedUserPrincipal.getAuthorities()
                )
        );
    }


    @GetMapping("/user/check-email")
    public EmailCheckResponse checkEmail(@RequestParam String email, @RequestParam Long userId) {
        boolean exists = userService.isEmailTaken(email, userId);
        return new EmailCheckResponse(exists);
    }


    // 회원탈퇴 화면
    @PostMapping("user/delete")
    public String userDelete(UserDto userDto) throws Exception{
        userService.deleteUser(userDto);
        return "redirect:/";
    }



}
