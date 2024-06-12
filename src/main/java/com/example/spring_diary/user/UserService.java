package com.example.spring_diary.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //회원가입
    public void createUser(UserDto userDto){
        Role role = userDto.getRole() != null ? userDto.getRole() : Role.ROLE_USER;

        User user1 = new User(userDto.getNickname(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getBirthday(), role);
        userRepository.save(user1);
    }


    //회원수정
    public void updateUser(UserDto userDto) throws Exception{
        User user1 = userRepository.findByUserId(userDto.getUserId());

        if (user1 == null) {
            // 해당 회원이 없으면 예외처리 발생
            System.out.println("해당 사용자를 찾을 수 없습니다.");
            throw new EntityNotFoundException();
        } else {
            user1.setNickname(userDto.getNickname());
            user1.setEmail(userDto.getEmail());
            user1.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user1.setBirthday(userDto.getBirthday());

            userRepository.save(user1);

        }

    }


    //회원삭제
    public void deleteUser(UserDto userDto) throws Exception{
        User user1 = userRepository.findByUserId(userDto.getUserId());

        if (user1 == null) {
            // 해당 회원이 없으면 예외처리 발생
            System.out.println("해당 사용자를 찾을 수 없습니다.");
            throw new EntityNotFoundException();
        } else {
            userRepository.delete(user1);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }


    private UserDetails toUserDetail(User user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.emptyList()) // 권한이 없는 경우 빈 리스트를 제공
                .build();
    }

    // 현재 로그인된 사용자 반환
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUser();
        }
        return null;
    }
}

