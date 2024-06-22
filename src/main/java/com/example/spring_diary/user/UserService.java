package com.example.spring_diary.user;

import com.example.spring_diary.diary.Diary;
import com.example.spring_diary.diary.DiaryRepository;
import com.example.spring_diary.summary.Summary;
import com.example.spring_diary.summary.SummaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SummaryRepository summaryRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SummaryRepository summaryRepository, DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.summaryRepository = summaryRepository;
        this.diaryRepository = diaryRepository;
    }


    //회원가입
    public void createUser(UserDto userDto){
        Role role = userDto.getRole() != null ? userDto.getRole() : Role.ROLE_USER;

        // 이메일 중복 검사
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("이메일이 이미 사용 중입니다.");
        }

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
            // 이메일 중복 검사
            if (!user1.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
                throw new IllegalArgumentException("이메일이 이미 사용 중입니다.");
            }

            user1.setNickname(userDto.getNickname());
            user1.setEmail(userDto.getEmail());
//            user1.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user1.setBirthday(userDto.getBirthday());

            userRepository.save(user1);

        }

    }


    // 회원탈퇴
    public void deleteUser(UserDto userDto) throws Exception {
        User user = userRepository.findByUserId(userDto.getUserId());

        if (user == null) {
            throw new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.");
        } else {
            // 해당 사용자가 작성한 모든 다이어리와 요약을 먼저 삭제합니다.
            List<Diary> diaries = diaryRepository.findAllByUser(user);
            for (Diary diary : diaries) {
                // 각 다이어리에 연결된 요약 삭제
                List<Summary> summaries = summaryRepository.findAllByDiary(diary);
                summaryRepository.deleteAll(summaries);

                // 다이어리 삭제
                diaryRepository.delete(diary);
            }

            // 최종적으로 사용자 삭제
            userRepository.delete(user);
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


    // 현재 로그인된 사용자 반환
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUser();
        }
        return null;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public boolean isEmailTaken(String email, Long userId) {
        User user = userRepository.findByEmail(email);
        return user != null && (userId == null || !(user.equals(userRepository.findByUserId(userId))));
    }

}

