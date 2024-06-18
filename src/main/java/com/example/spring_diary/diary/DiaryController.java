package com.example.spring_diary.diary;

import com.example.spring_diary.summary.SummaryService;
import com.example.spring_diary.user.User;
import com.example.spring_diary.user.UserPrincipal;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private SummaryService summaryService;


    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        if (userPrincipal == null || userPrincipal.getUser() == null) {
            return "redirect:/user/login";
        }

        User user = userPrincipal.getUser();
        model.addAttribute("nickname", user.getNickname());

        List<Diary> uploadedDiaries = diaryService.readAllUploaded();
        model.addAttribute("uploadedDiaries", uploadedDiaries);

        return "home";
    }

//    글 생성 화면
    @GetMapping("/diary/new")
    public String newDiaryForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("diaryDto", new DiaryDto()); // 빈 DiaryDto 객체 추가
        return "diary/diary-register"; // 다이어리 작성 폼으로 이동
    }

    @PostMapping("/diary/save")
    public String saveDiary(@ModelAttribute DiaryDto diaryDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        // 다이어리 저장
        diaryService.createDiary(diaryDto);

        return "redirect:/home"; // 저장 후 홈 화면으로 리디렉션
    }

    @GetMapping("diary/read/un-uploaded")
    public String readUnUploadedDiary(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        model.addAttribute("nickname", user.getNickname());
        List<Diary> temporaryDiaries = diaryService.readAllUnUploaded();
        List<DiaryDto> temporaryDiaryDtos = temporaryDiaries.stream().map(DiaryDto::fromEntity).toList();
        model.addAttribute("temporaryDiaries", temporaryDiaryDtos);
        return "diary/diary-readTemporary";
    }

    // 다이어리 수정 페이지로 이동
    @GetMapping("/diary/edit/{id}")
    public String editDiary(@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        model.addAttribute("nickname", user.getNickname());

        // 다이어리 ID로 다이어리 엔티티를 찾음
        Diary diary = diaryService.getDiaryById(id);
        DiaryDto diaryDto = DiaryDto.fromEntity(diary);
        model.addAttribute("diaryDto", diaryDto);

        return "diary/diary-edit";
    }

    @GetMapping("diary/edit-saved")
    public String editSavedDiary(@RequestParam("id") long id, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        model.addAttribute("nickname", user.getNickname());
        Diary diary = diaryService.getDiaryById(id);
        DiaryDto diaryDto = DiaryDto.fromEntity(diary);
        model.addAttribute("diaryDto", diaryDto);
        return "diary/diary-edit-saved";
    }

    @PostMapping("diary/delete")
    public String deleteDiary(DiaryDto diaryDto) {
        diaryService.deleteDiary(diaryDto);
        return "redirect:/home";
    }


    @GetMapping("/diary/view/{id}")
    public String viewDiary(@PathVariable("id") Long id, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        Diary diary = diaryService.getDiaryById(id);
        model.addAttribute("diary", diary);

        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setDiaryId(diary.getDiaryId());
        model.addAttribute("summaries", summaryService.getSummariesByDiaryId(diaryDto));

        return "diary/diary-read";
    }

}


