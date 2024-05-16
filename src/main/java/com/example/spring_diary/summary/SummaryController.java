package com.example.spring_diary.summary;

import com.example.spring_diary.openaiApi.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
public class SummaryController {

//    한 번 주입된 객체는 변경되지 않는 것이 좋으므로 final로 설정 <-- 다시 체크해보고 공부하기
        private final SummaryService summaryService;

        @Autowired
        public SummaryController(SummaryService summaryService) {
            this.summaryService = summaryService;
        }

        @PostMapping("summary/create")
        public String summarizeText(@RequestBody String question) {
            return summaryService.summarize(question);
        }

}



