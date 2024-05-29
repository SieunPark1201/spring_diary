package com.example.spring_diary.diary;

import com.example.spring_diary.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findByDiaryId(long diaryId);

    List<Diary> findAllByUploaded(boolean Uploaded);

    List<Diary> findAllByUserAndUploaded(User user, boolean uploaded);

}
