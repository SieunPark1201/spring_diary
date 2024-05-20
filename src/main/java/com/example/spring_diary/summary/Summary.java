package com.example.spring_diary.summary;

import com.example.spring_diary.diary.Diary;
import com.example.spring_diary.diary.DiaryDto;
import com.example.spring_diary.picture.Picture;
import com.example.spring_diary.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Summary {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long summaryId;

    @Column(length = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "diary_diaryId")
    private Diary diary;

    @OneToMany(mappedBy = "summary", fetch = FetchType.LAZY)
    private List<Picture> pictureList;

    public Summary(String content){
        this.content = content;
    }



}
