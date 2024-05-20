package com.example.spring_diary.diary;

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
//        기존 DB에 저장되어 있는 글인지 조회 후 데려오기
        if (!diaryRepository.existsById(diaryDto.getDiaryId())){
            throw new EntityNotFoundException();
        } else {
            Diary beforeUpdateDiary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());
            beforeUpdateDiary.setContent(diaryDto.getContent());
            diaryRepository.save(beforeUpdateDiary);
        }

    }


    //글 임시저장->저장 수정
    public void uploadDiary(DiaryDto diaryDto) throws Exception {
        Diary unuploadedDiary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());
        if (unuploadedDiary == null || !unuploadedDiary.getUser().equals(getCurrentUser())) {
            throw new Exception("권한이 없습니다.");
        }

        if (unuploadedDiary.isUploaded()){
            throw new Exception();   // 좀 더 구체적인 Exception으로 나중에 수정하기
        } else {
            unuploadedDiary.setUploaded(true);
            diaryRepository.save(unuploadedDiary);
        }
    }

    // 글 조회-날짜별 && uploaded == true
    public List<Diary> readAllUploaded(){
        User user = getCurrentUser();
        List<Diary> AllUploadedDiary = (diaryRepository.findAllByUser(user)).findAllByUploaded(true);
        return AllUploadedDiary;
    }

    // 글 조회-uploaded == false
    public List<Diary> readAllUnUploaded(){
        User user = getCurrentUser();
        List<Diary> AllUnUploadedDiary = (diaryRepository.findAllByUser(user)).findAllByUploaded(false);
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
            diaryRepository.delete(deletingDiary);
        }

    }

}
