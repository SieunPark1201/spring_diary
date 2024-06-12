package com.example.spring_diary.diary;

import com.example.spring_diary.summary.SummaryService;
import com.example.spring_diary.user.User;
import com.example.spring_diary.user.UserPrincipal;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@GetMapping("diary/new")
public String newDiary(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model, @RequestParam(name = "date", required = false) String date) {
    User user = userPrincipal.getUser();
    DiaryDto diaryDto = new DiaryDto();
    diaryDto.setDate(date != null ? LocalDate.parse(date) : LocalDate.now());
    model.addAttribute("nickname", user.getNickname());
    model.addAttribute("diaryDto", diaryDto);
    return "diary/diary-register";
}

    @PostMapping("diary/new")
    public String newDiary(DiaryDto diaryDto) {
        diaryService.createDiary(diaryDto);
        return "redirect:/home";
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

    @GetMapping("diary/edit")
    public String editDiary(@RequestParam("id") long id, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        model.addAttribute("nickname", user.getNickname());
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


