package com.example.spring_diary.summary;
import com.example.spring_diary.openaiApi.ChatRequest;
import com.example.spring_diary.openaiApi.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "summaryClient", url = "https://api.openai.com/v1/")
public interface SummaryClient {

    // completions 엔드포인트는 OpenAI API의 텍스트 생성 기능을 호출하는 기본 경로
    @PostMapping("chat/completions")
    ResponseEntity<ChatResponse> summarizeclient(
            @RequestHeader("Authorization") String apiKey,
            @RequestBody ChatRequest request
    );

}
