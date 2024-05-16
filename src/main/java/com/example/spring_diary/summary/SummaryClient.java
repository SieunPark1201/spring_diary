package com.example.spring_diary.summary;
import com.example.spring_diary.openaiApi.ChatRequest;
import com.example.spring_diary.openaiApi.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "summaryClient", url = "http://localhost:8080")
public interface SummaryClient {

    @PostMapping("/summary/create")
    ResponseEntity<ChatResponse> summarizeclient(
            @RequestHeader("Authorization") String apiKey,
            @RequestBody ChatRequest request
    );

}
