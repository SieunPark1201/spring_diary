package com.example.spring_diary.summary;

import com.example.spring_diary.openaiApi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryClient summaryClient;
    private final static String ROLE_USER = "user";
    private final static String MODEL = "gpt-3.5-turbo";


    @Value("${apikey}")
    private String apiKey;




//    ChatGPT 이용하여 요약
    public String summarize(final String question) {
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(question+" ... 이건 내가 쓴 일기야. 일기의 특성을 고려해서 내용을 요약해줄래? 요약에서 새로운 인사이트를 얻을 수 있도록 네가 몇몇 부분을 재해석해도 돼. 다른 말은 붙이지 말고 그냥 요약문 자체만 출력해줘.")
                .build();

        ChatRequest chatRequest = ChatRequest.builder()
                .model(MODEL)
                .messages(Collections.singletonList(message))
                .build();

        return summaryClient
                .summarizeclient(apiKey,chatRequest)
                .getBody()
                .getChoices()
                .stream()
                .findFirst()
                .orElseThrow()
                .getMessage()
                .getContent();
    }
}
