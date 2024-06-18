package com.example.spring_diary.diary;

import com.example.spring_diary.summary.Summary;
import com.example.spring_diary.summary.SummaryRepository;
import com.example.spring_diary.summary.SummaryService;
import com.example.spring_diary.user.User;
import com.example.spring_diary.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SummaryRepository summaryRepository;

    // 현재 로그인된 사용자 정보 가져오기
    private User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            String email = ((UserDetails) principal).getUsername(); // UserDetails의 getUsername() 메소드가 email을 반환하도록 설정
            return userRepository.findByEmail(email);
        } else {
            throw new IllegalStateException("로그인된 사용자를 찾을 수 없습니다.");
        }
    }




    // 글 생성
    public void createDiary(DiaryDto diaryDto){
        User user = getCurrentUser();
        Diary newDiary = new Diary(diaryDto.getContent(), diaryDto.getDate(), diaryDto.isUploaded());
        newDiary.setUser(user);  //user를 현재 로그인된 계정으로 설정
        diaryRepository.save(newDiary);
    }

    // 글 내용 수정
    public void updateDiary(DiaryDto diaryDto) throws EntityNotFoundException{
        // 다이어리 ID로 기존 다이어리 찾기
        Diary existingDiary = diaryRepository.findById(diaryDto.getDiaryId())
                .orElseThrow(() -> new EntityNotFoundException("Diary not found"));

        // 기존 다이어리 정보 업데이트
        existingDiary.setContent(diaryDto.getContent());
        existingDiary.setDate(diaryDto.getDate());
        existingDiary.setUploaded(diaryDto.isUploaded());

        // 다이어리 저장
        diaryRepository.save(existingDiary);
    }



    // 글 조회-날짜별 && uploaded == true
    public List<Diary> readAllUploaded(){
        User user = getCurrentUser();
        List<Diary> AllUploadedDiary = diaryRepository.findAllByUserAndUploaded(user, true);
        return AllUploadedDiary;
    }

    // 글 조회-uploaded == false
    public List<Diary> readAllUnUploaded(){
        User user = getCurrentUser();
        List<Diary> AllUnUploadedDiary = diaryRepository.findAllByUserAndUploaded(user, false);
        return AllUnUploadedDiary;
    }

    // 글 삭제
    public void deleteDiary(DiaryDto diaryDto) throws EntityNotFoundException {
        Diary diary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());

        if (!diaryRepository.existsById(diaryDto.getDiaryId())) {
            throw new EntityNotFoundException();
        } else if (diary == null || !diary.getUser().equals(getCurrentUser())) {
                throw new EntityNotFoundException("권한이 없습니다.");
        } else {
            Diary deletingDiary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());
            List<Summary> summaries = summaryRepository.findAllByDiaryDiaryId(diaryDto.getDiaryId());
            summaryRepository.deleteAll(summaries);
            diaryRepository.delete(deletingDiary);
        }

    }

    public Diary getDiaryById(long id) {
        return diaryRepository.getById(id);
    }
}
