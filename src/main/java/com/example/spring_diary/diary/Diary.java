package com.example.spring_diary.diary;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long diaryId;

    @Column(length = 5000)
    private String content;

    @Column
    private LocalDate date;

    @Column
    private boolean uploaded;    // enum으로 하는 게 코드 가독성이 더 좋으려나? -> 굳이...


    public Diary(String content, LocalDate date, boolean uploaded) {
        this.content = content;
        this.date = date;
        this.uploaded = uploaded;
    }


}
