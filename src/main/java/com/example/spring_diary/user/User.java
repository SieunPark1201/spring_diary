package com.example.spring_diary.user;

import com.example.spring_diary.diary.Diary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 20)
    private String nickname;

    @Column(length = 50)
    private String email;

    @Column(length = 20)
    private String password;

    @Column
    private LocalDate birthday;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Diary> diaryList;


    @Builder
    public User(String nickname, String email, String password, LocalDate birthday){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }
}
