package com.example.spring_diary.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findByDiaryId(long diaryId);

    List<Diary> findAllByIsUploaded(boolean isUploaded);
}
