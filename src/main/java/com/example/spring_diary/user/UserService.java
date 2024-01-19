package com.example.spring_diary.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //회원가입
    public void createUser(UserDto userDto){
        User user1 = new User(userDto.getNickname(), userDto.getEmail(),
                userDto.getPassword(), userDto.getBirthday());
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
            user1.setPassword(userDto.getPassword());
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


}
