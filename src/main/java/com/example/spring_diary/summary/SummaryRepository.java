package com.example.spring_diary.summary;

import com.example.spring_diary.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    List<Summary> findAllByDiaryDiaryId(long diaryId);

    Summary findBySummaryId(long summaryId);

    List<Summary> findAllByDiary(Diary diary);
}
