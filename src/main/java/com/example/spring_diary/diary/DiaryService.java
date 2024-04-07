package com.example.spring_diary.diary;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if (unuploadedDiary.isUploaded()){
            throw new Exception();   // 좀 더 구체적인 Exception으로 나중에 수정하기
        } else {
            unuploadedDiary.setUploaded(true);
            diaryRepository.save(unuploadedDiary);
        }
    }

    // 글 조회-날짜별 && uploaded == true
    public List<Diary> readAllUploaded(){
        List<Diary> AllUploadedDiary = diaryRepository.findAllByUploaded(true);
        return AllUploadedDiary;
    }

    // 글 조회-uploaded == false
    public List<Diary> readAllUnUploaded(){
        List<Diary> AllUnUploadedDiary = diaryRepository.findAllByUploaded(false);
        return AllUnUploadedDiary;
    }

    // 글 삭제
    public void deleteDiary(DiaryDto diaryDto) throws EntityNotFoundException {

        if (!diaryRepository.existsById(diaryDto.getDiaryId())){
            throw new EntityNotFoundException();
        } else {
            Diary deletingDiary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());
            diaryRepository.delete(deletingDiary);
        }

    }

}
