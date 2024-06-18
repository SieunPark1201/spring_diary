package com.example.spring_diary.summary;

import com.example.spring_diary.diary.Diary;
import com.example.spring_diary.diary.DiaryDto;
import com.example.spring_diary.diary.DiaryService;
import com.example.spring_diary.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SummaryController {

//    한 번 주입된 객체는 변경되지 않는 것이 좋으므로 final로 설정 <-- 다시 체크해보고 공부하기
        private final SummaryService summaryService;

        private final DiaryService diaryService;

        @Autowired
        public SummaryController(SummaryService summaryService, DiaryService diaryService) {
            this.summaryService = summaryService;
            this.diaryService = diaryService;
        }

        @PostMapping("summary/create")
        public String summarizeText(Long diaryId) {
            DiaryDto diaryDto = new DiaryDto();
            diaryDto.setDiaryId(diaryId);
            Diary diary = diaryService.getDiaryById(diaryId);
            diaryDto.setContent(diary.getContent());

            return summaryService.summarize(diaryDto);
        }

    @PostMapping("/summary/delete")
    public String summaryDelete(@RequestParam("summaryId") long summaryId) throws Exception {
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setSummaryId(summaryId);

        // Summary 삭제 서비스 호출
        summaryService.deleteSummary(summaryDto);

        return "redirect:/"; // 삭제 후 리디렉션
    }

}



