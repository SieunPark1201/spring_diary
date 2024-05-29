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

    public static DiaryDto fromEntity(Diary diary) {
        DiaryDto dto = new DiaryDto();
        dto.setDiaryId(diary.getDiaryId());
        dto.setContent(diary.getContent());
        dto.setDate(diary.getDate());
        dto.setUploaded(diary.isUploaded());
        return dto;
    }
}
