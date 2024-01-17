package com.example.spring_diary.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {

    @Autowired
    DiaryRepository diaryRepository;

}
