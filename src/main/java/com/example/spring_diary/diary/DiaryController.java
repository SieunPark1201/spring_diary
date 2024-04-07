package com.example.spring_diary.diary;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }


//    글 생성 화면
    @GetMapping("diary/new")
    public String newDiary(){ return "diary/diary-register"; }

    @PostMapping("diary/new")
    public String newDiary(DiaryDto diaryDto){
        diaryService.createDiary(diaryDto);
        return "redirect:/";
    }


//    글 수정 화면
    @PostMapping("diary/update")
    public String updateDiary(DiaryDto diaryDto){
        diaryService.updateDiary(diaryDto);
        return "redirect:/";
    }


//    글 전체 조회 화면
    @GetMapping("diary/read/uploaded")
    public String readDiary(){
        diaryService.readAllUploaded();
        return "diary/diary-readAllUploaded";
    }


//     글 조회-uploaded == false 화면
    @GetMapping("diary/read/un-uploaded")
    public String readUnUploadedDiary(){
        diaryService.readAllUnUploaded();
        return "diary/diary-readTemporary";
    }


//    글 삭제 화면
    @PostMapping("diary/delete")
    public String deleteDiary(DiaryDto diaryDto){
        diaryService.deleteDiary(diaryDto);
        return "redirect:/";
    }



}
