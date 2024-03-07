package com.example.spring_diary.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {

    @Autowired
    DiaryRepository diaryRepository;

    // 글 생성
    public void createDiary(DiaryDto diaryDto){
        Diary newDiary = new Diary(diaryDto.getContent(), diaryDto.getDate(), diaryDto.isUploaded());
        diaryRepository.save(newDiary);
    }

    // 글 내용 수정
    public void updateDiary(DiaryDto diaryDto) throws Exception{
        Diary beforeUpdateDiary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());
    }


    //글 임시저장->저장 수정


    // 글 조회-날짜별 && uploaded == true


    // 글 조회-uploaded == false


    // 글 삭제

}
