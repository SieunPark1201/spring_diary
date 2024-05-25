package com.example.spring_diary.diary;

import com.example.spring_diary.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("nickname", user.getNickname());
        return "home";
    }

//    글 생성 화면
    @GetMapping("diary/new")
public String newDiary(HttpSession session, Model model, @RequestParam(name = "date", required = false) String date) {
    User user = (User) session.getAttribute("loggedInUser");
    model.addAttribute("nickname", user.getNickname());
    model.addAttribute("date", date != null ? LocalDate.parse(date) : LocalDate.now());
    return "diary/diary-register";
}

    @PostMapping("diary/new")
    public String newDiary(DiaryDto diaryDto, HttpSession session) {
        diaryService.createDiary(diaryDto);
        return "redirect:/home";
    }


////    글 수정 화면
//    @PostMapping("diary/update")
//    public String updateDiary(DiaryDto diaryDto){
//        diaryService.updateDiary(diaryDto);
//        return "redirect:/";
//    }


    @GetMapping("diary/read/uploaded")
    public String readDiary(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("nickname", user.getNickname());
        List<Diary> uploadedDiaries = diaryService.readAllUploaded();
        model.addAttribute("uploadedDiaries", uploadedDiaries);
        return "redirect:/home";
    }

    @GetMapping("diary/read/un-uploaded")
    public String readUnUploadedDiary(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("nickname", user.getNickname());
        List<Diary> temporaryDiaries = diaryService.readAllUnUploaded();
        model.addAttribute("temporaryDiaries", temporaryDiaries);
        return "diary/diary-readTemporary";
    }

    @GetMapping("diary/edit")
    public String editDiary(@RequestParam("id") long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("nickname", user.getNickname());
        Diary diary = diaryService.getDiaryById(id);
        model.addAttribute("diary", diary);
        return "diary/diary-edit";
    }

    @GetMapping("diary/edit-saved")
    public String editSavedDiary(@RequestParam("id") long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("nickname", user.getNickname());
        Diary diary = diaryService.getDiaryById(id);
        model.addAttribute("diary", diary);
        return "diary/diary-editTemporary";
    }

    @PostMapping("diary/delete")
    public String deleteDiary(DiaryDto diaryDto) {
        diaryService.deleteDiary(diaryDto);
        return "redirect:/";
    }
}


