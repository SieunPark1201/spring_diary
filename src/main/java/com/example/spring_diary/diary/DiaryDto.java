package com.example.spring_diary.diary;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiaryDto {

    private long diaryId;
    private String content;

    private LocalDate date;

    private boolean uploaded;
}
